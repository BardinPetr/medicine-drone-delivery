FROM gradle:8-jdk21-alpine as build
WORKDIR /app

COPY gradle ./
COPY gradlew ./
COPY gradle.properties ./
COPY *.gradle.kts ./
COPY src src

RUN gradle bootJar

FROM eclipse-temurin:21-jre-alpine as run

WORKDIR /app

ENV PYTHONUNBUFFERED=1
RUN apk add --update --no-cache python3 && ln -sf python3 /usr/bin/python
RUN python3 -m ensurepip
RUN pip3 install --no-cache --upgrade pip setuptools
RUN pip3 install extremitypathfinder

COPY router.py .

FROM run

COPY run.sh .
COPY --from=build /app/build/libs/meddelivery.jar ./app.jar

ENTRYPOINT ["./run.sh"]
