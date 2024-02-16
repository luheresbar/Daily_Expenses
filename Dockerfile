FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install gradle -y
RUN gradle clean build

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /Daily-Expenses.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

# FROM amazoncorretto:17-alpine-jdk

# COPY Daily-Expenses.jar app.jar

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "/app.jar"]