FROM python:3.8
WORKDIR /ai
COPY . .
RUN pip install -r requirements.txt

RUN pip install git+https://git@github.com/SKTBrain/KoBERT.git@master

RUN pip install 'git+https://github.com/SKTBrain/KoBERT.git#egg=kobert_tokenizer&subdirectory=kobert_hf'

RUN pip install torch==1.13.1

EXPOSE 8000
CMD ["uvicorn","main:app","--reload", "--host", "0.0.0.0", "--port", "8000"]
