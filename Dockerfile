FROM openjdk:16-jdk-alpine
EXPOSE 8080
ADD target/yahoo-rest-api.jar yahoo-rest-api.jar
ENTRYPOINT ["java", "-jar", "yahoo-rest-api.jar"]