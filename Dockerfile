FROM gradle:8.14.3-jdk21-jammy AS build
WORKDIR /app

COPY gradle gradlew gradle.properties ./
COPY build.gradle.kts settings.gradle.kts ./

RUN gradle dependencies --no-daemon --info

COPY gradle gradle
COPY src src

RUN rm /app/src/main/proto/service.proto
COPY python/service.proto /app/src/main/proto/service.proto

RUN gradle --no-daemon --parallel bootJar

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/meddelivery.jar ./app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar", "generate"]
