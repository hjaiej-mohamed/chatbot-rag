FROM python:3.10-slim

WORKDIR /app

COPY . .

RUN pip install --upgrade -r requirements.txt --no-cache

CMD ["uvicorn","chat_pgv:app", "--host","0.0.0.0"]