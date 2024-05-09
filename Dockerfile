FROM gradle:8.7.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean build -x test