FROM python:3.8
WORKDIR /ai
COPY . .
RUN pip install -r requirements.txt

RUN pip install git+https://github.com/openai/CLIP.git

RUN pip install git+https://git@github.com/SKTBrain/KoBERT.git@master

RUN pip install 'git+https://github.com/SKTBrain/KoBERT.git#egg=kobert_tokenizer&subdirectory=kobert_hf'

RUN pip install torch==1.13.1

#### 필요성 재고 필요
RUN pip install python-multipart
RUN apt-get update && apt-get install ffmpeg libsm6 libxext6  -y
####

EXPOSE 8000
CMD ["uvicorn","main:app","--reload", "--host", "0.0.0.0", "--port", "8000"]