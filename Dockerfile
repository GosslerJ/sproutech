FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim
WORKDIR /app
COPY ./build/libs/sproutech-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]