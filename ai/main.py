from fastapi import FastAPI
from dotenv import load_dotenv
import os
import openai

app = FastAPI()

#open api key
load_dotenv()
openai.api_key = os.getenv("SECRET_KEY")

@app.get("/")
async def root():
    return {"message":"Hello World"}

#test
#e.g.:http://127.0.0.1:8000/hello/daehyuck
@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message":f"Hello {name}"}


@app.post("/reviews/gpt")
async def create_review(title:str, words: str, writer=None, char=None):
    
    # message 구성
    if writer != None and char != None:
        
        #default number of character value
        char = max(100,char)
            
        m = f"제목:{title}, 키워드:{words}, 작가:{writer}, 서평 {char}자 이내"
    
    elif writer == None:
        
        #default number of character value
        char = max(100,char)
        
        m = f"제목:{title}, 키워드:{words}, 서평 {char}자 이내"
    
    elif char == None:
        
        m = f"제목:{title}, 키워드:{words}, 작가:{writer}, 서평 80자 이내"
    
    #chatgpt request
    completion = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {"role": "user", "content": m}
    ]
    )
    
    #chatgpt response
    return completion.choices[0].message