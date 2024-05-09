FROM gradle:8.7.0-jdk17 AS build

COPY --chown=gradle:gradle build.gradle settings.gradle /home/gradle/project/
COPY --chown=gradle:gradle /src /home/gradle/project/src/

WORKDIR /home/gradle/project