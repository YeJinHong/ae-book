# local에서 fastapi 실행하는 순서

0. 작업환경 이동

cd ai

1. 가상환경 설치

python -m venv venv

2. 가상환경 실행

source venv/Scripts/activate

3. 패키지 설치

pip install -r requirements.txt

4. 서버 실행
uvicorn main:app --reload

# docs

fastapi에서는 swagger 형식의 docs를 제공

http://127.0.0.1:8000/docs