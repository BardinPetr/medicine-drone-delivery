FROM gradle:8.14.3-jdk21-alpine AS build
WORKDIR /app

COPY gradle gradlew gradle.properties ./
COPY build.gradle.kts settings.gradle.kts ./

RUN gradle dependencies --no-daemon --info

COPY gradle gradle
COPY src src

RUN gradle --no-daemon --parallel bootJar

FROM eclipse-temurin:21-jre-alpine AS pyrun

WORKDIR /app

ENV PYTHONUNBUFFERED=1
RUN apk add --update --no-cache python3

RUN mkdir /app/python
COPY python/requirements.txt /app/python/
RUN cd /app/python && python3 -m venv venv && source venv/bin/activate && pip install -r requirements.txt

COPY python/router.py python/run.sh /app/python/

FROM pyrun

COPY --from=build /app/build/libs/meddelivery.jar ./app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar", "generate"]
