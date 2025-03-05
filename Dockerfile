FROM openjdk:17-jdk-slim
WORKDIR /app
# fazer o build do back mvn clean package
COPY target/green-learning-0.0.1-SNAPSHOT.jar green-learning-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "green-learning-0.0.1-SNAPSHOT.jar"]