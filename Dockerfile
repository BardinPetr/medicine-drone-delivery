FROM gradle:8-jdk21-alpine as build
WORKDIR /app

COPY gradle ./
COPY gradlew ./
COPY gradle.properties ./
COPY *.gradle.kts ./
COPY src src

RUN gradle bootJar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY run.sh .
COPY --from=build /app/build/libs/meddelivery.jar ./app.jar

ENTRYPOINT ["./run.sh"]
