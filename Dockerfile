FROM amazoncorretto:17-alpine-jdk

COPY  Daily-Expenses.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]