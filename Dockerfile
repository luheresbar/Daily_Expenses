FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

# Cambiar los permisos de ejecución del archivo gradlew
RUN chmod +x gradlew

# Ejecutar el comando Gradle
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8081

COPY --from=build /build/libs/daily-expenses-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]