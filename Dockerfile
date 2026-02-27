FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY target/backendDevTest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]