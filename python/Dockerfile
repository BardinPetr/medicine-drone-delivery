FROM python:3.13-alpine

EXPOSE 50051
WORKDIR /app

RUN apk --no-cache add musl-dev g++

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

CMD ["python", "/app/router.py"]
