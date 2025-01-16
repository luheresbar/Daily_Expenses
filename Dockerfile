# Etapa de construcción
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Copiar archivos necesarios
COPY . .

# Verificar la presencia del archivo gradlew
RUN ls -l /app/gradlew

# Dar permisos de ejecución al archivo gradlew
RUN chmod +x /app/gradlew

# Ejecutar Gradle para generar el archivo JAR
RUN /app/gradlew bootJar --no-daemon

# Etapa de ejecución final
FROM openjdk:17-alpine
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081
CMD ["java", "-jar", "app.jar"]