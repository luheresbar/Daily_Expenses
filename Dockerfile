FROM amazoncorretto:17-alpine-jdk

# Exponer el puerto 8080
EXPOSE 8080

COPY Daily-Expenses.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]