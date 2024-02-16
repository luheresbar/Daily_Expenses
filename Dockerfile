FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

# Instala Gradle
RUN apt-get install wget unzip -y
RUN wget https://services.gradle.org/distributions/gradle-7.3-bin.zip
RUN mkdir /opt/gradle
RUN unzip -d /opt/gradle gradle-7.3-bin.zip
ENV PATH="/opt/gradle/gradle-7.3/bin:${PATH}"

COPY . .

# Ejecuta el comando Gradle con opciones de depuración
RUN gradle clean build --stacktrace

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /Daily-Expenses.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

# FROM ubuntu:latest AS build

# RUN apt-get update
# RUN apt-get install openjdk-17-jdk -y
# COPY . .

# RUN apt-get install gradle -y
# RUN gradle clean build

# FROM openjdk:17-jdk-slim

# EXPOSE 8080

# COPY --from=build /Daily-Expenses.jar app.jar

# ENTRYPOINT [ "java", "-jar", "app.jar" ]

# FROM amazoncorretto:17-alpine-jdk

# COPY Daily-Expenses.jar app.jar

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "/app.jar"]