FROM gradle:8.14.3-jdk21-alpine as build
WORKDIR /app

COPY gradle gradlew gradle.properties ./
COPY build.gradle.kts settings.gradle.kts ./

RUN gradle --no-daemon --parallel dependencies

COPY gradle gradle
COPY src src

RUN gradle --no-daemon --parallel bootJar

FROM eclipse-temurin:21-jre-alpine as run

WORKDIR /app

ENV PYTHONUNBUFFERED=1
RUN apk add --update --no-cache python3 && ln -sf python3 /usr/bin/python
RUN python3 -m ensurepip
RUN pip3 install --no-cache --upgrade pip setuptools
RUN pip3 install extremitypathfinder

COPY python/router.py .

FROM run

COPY --from=build /app/build/libs/meddelivery.jar ./app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
