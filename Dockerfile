FROM ubuntu:latest AS build

# Instalación de Java JDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk

# Instalación de Gradle
RUN apt-get install -y wget unzip && \
    wget -q --show-progress --progress=bar:force:noscroll --https-only --timestamping https://services.gradle.org/distributions/gradle-8.3-bin.zip && \
    unzip -q -d /opt/gradle gradle-8.3-bin.zip && \
    rm gradle-8.3-bin.zip

# Configuración de las variables de entorno para Gradle
ENV GRADLE_HOME=/opt/gradle/gradle-8.3
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Copiar archivos del proyecto y compilar con Gradle
COPY . .

RUN gradle clean build

# Etapa final
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