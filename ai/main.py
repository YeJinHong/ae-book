from fastapi import FastAPI,File,UploadFile
from fastapi.responses import JSONResponse
from review_star_prediction import *
from isbn_ocr import *
from dotenv import load_dotenv
import os
import openai
import io
import base64
import cv2
import sys
import urllib.request
import requests
import numpy as np
from PIL import Image
from typing import Dict,Any

from starlette.middleware.cors import CORSMiddleware

origins = [
    "http://127.0.0.1",
    "http://127.0.0.1:3000",
    "http://localhost:3000"
]

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

#constant
STT_URL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=Kor"
TTS_URL = "httxps://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts"

#api key
load_dotenv()
openai.api_key = os.getenv("SECRET_KEY")
client_id = os.getenv("CLIENT_ID")
client_secret = os.getenv("CLIENT_SECRET")

@app.get("/")
async def root():
    return {"message":"Hello World"}

#test
#e.g.:http://127.0.0.1:8000/hello/daehyuck
@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message":f"Hello {name}"}

"""
input: title, words
output: review, star point
"""
@app.post("/reviews/gpt")
async def create_review(title:str, words: str, writer=None, char=None):
    
    # {작가}의 {제목} 책을 읽고 {키워드}를 키워드로 해서 서평을 {char} 자 이내로 써줘.
    # message 구성
    if writer != None and char != None:
        
        #default number of character value
        char = max(100,int(char))

        m = f"너는 출판사에서 일하는 직원이야. {writer}의 {title} 책을 읽고 {words}를 키워드로 해서 서평을 {char}자 이내로 써줘"
    elif writer == None:
        
        #default number of character value
        if char == None:
            char = 100
        else:
            char = max(100,int(char))
        
        m = f"너는 출판사에서 일하는 직원이야. {title} 책을 읽고 {words}를 키워드로 해서 서평을 {char}자 이내로 써줘"
    
    elif char == None:
        
        m = f"너는 출판사에서 일하는 직원이야. {writer}의 {title} 책을 읽고 {words}를 키워드로 해서 서평을 100자 이내로 써줘"
    
    #chatgpt request
    completion = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "user", "content": m}
    ]
    )
    
    #chatgpt response
    response = completion.choices[0].message['content']
    
    #predicted star point
    star = predict_star_point(response)
    
    return {"review":response, "star":star}


"""
input:mp3 file(keyword), title
output:review & point prediction
"""
@app.post("/reviews/sound")
async def sound_to_review(title:str, sound: UploadFile = File(...), writer=None, char=None):
    
    #read mp3 file to byte string
    data = await sound.read()
    
    headers = {
        "X-NCP-APIGW-API-KEY-ID": client_id,
        "X-NCP-APIGW-API-KEY": client_secret,
        "Content-Type": "application/octet-stream"
    }
    
    response = requests.post(STT_URL,  data=data, headers=headers)
    rescode = response.status_code
    
    if(rescode == 200):
        
        words = response.text #stt result
        
        review_dict = create_gpt_review(title,words,writer,char) #create review & star
        
        return {"review":review_dict["review"], "star":review_dict["star"]}
    else:
        return "Error : " + response.text
    
    

#convert image to sketch
@app.post("/paintings/sketch")
async def image_to_sketch(image: UploadFile = File(...)):
    
    #read image data
    img = await image.read()
    
    #convert image to byte string
    img = np.fromstring(img,dtype=np.uint8)
    
    #read image
    img = cv2.imdecode(img,cv2.IMREAD_COLOR)
    
    #convert the color image to grayscale
    gray_image = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    
    #apply inverted filter
    inverted_image = 255 - gray_image
    
    #apply gaussian blur
    blur = cv2.GaussianBlur(inverted_image,(21,21),0)
    
    #convert blur image to inverted blur
    inv_blur = 255 - blur
    
    #convert it to sketch image
    #sketch can be obtained by performing bit-wise division
    #between grayscale image and inverted-blur image
    sketch = cv2.divide(gray_image, inv_blur, scale = 256.0)
    
    #convert sketch image to binary string
    binary_sketch = cv2.imencode('.jpg',sketch)[1].tobytes()
    
    # encoding byte string to base64
    encoded = base64.b64encode(binary_sketch)
    
    # decoding base64 to ascii
    decoded = encoded.decode('ascii')
    
    #return json response
    return JSONResponse(decoded)


"""
input: isbn image
output: isbn string
"""
@app.post("/books/isbn")
async def isbn_detection(image: UploadFile = File(...)):
    
    #read image data
    img = await image.read()
    
    #convert image to byte string
    img = np.fromstring(img,dtype=np.uint8)
    
    #read image
    img = cv2.imdecode(img,cv2.IMREAD_COLOR)
    
    #detect
    result = ocr.ocr(img,cls = True)
    
    #find isbn string
    for idx in range(len(result)):
        res = result[idx]
        for line in res:
            
            data = line[1][0]
            
            #success
            if 'ISBN' in data:
                
                #simple cleansing
                
                #case1 : ISBN 979-11-6050-443-9 (with white space)
                if data[4] == ' ':
                    
                    data = data[5:].replace('-','').strip()
                
                #case2 : ISBN979-11-6050-443-9 (no white space)
                elif data[4] >= '0' and data[4] <= '9':
                    
                    data = data[4:].replace('-','').strip()
                                
                return {"status":1, "data":data} 
    
    #fail
    return {"status":0, "data":""}

"""
input: story keyword
output: chatgpt story
"""
@app.post("/stories/gpt")
async def create_story(text:Dict[Any,Any]):
    
    print(text)
    
    #chatgpt query
    query = f"너는 동화 작가야. 8세 이하의 아동을 위해서 {text['text']}로 귀여운 동화를 만들어야 해. 아이들이 쉽게 이해할 수 있는 말로 구성해줘."
    
    print(query)
    
    #chatgpt request
    completion = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "user", "content": query}
    ]
    )
    
    #chatgpt response
    return {'data':completion.choices[0].message['content']}

"""
input:text
output:naver clova mp3 response data
"""
@app.post("/stories/sound")
async def text_to_sound(text: str):
    
    #create data(default)
    encText = urllib.parse.quote(text)
    data = "speaker=nminsang&volume=-5&speed=0&pitch=0&format=wav&text=" + encText
    
    #header
    request = urllib.request.Request(TTS_URL)
    request.add_header("X-NCP-APIGW-API-KEY-ID",client_id)
    request.add_header("X-NCP-APIGW-API-KEY",client_secret)
    
    #response
    response = urllib.request.urlopen(request, data=data.encode('utf-8'))
    rescode = response.getcode()
    
    
    if(rescode==200):
        
        #sound byte string    
        return response

    else:
        return "Error Code:" + rescode