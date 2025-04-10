# Etapa de build: construa o JAR com Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copie o arquivo pom.xml e baixe as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o código fonte e faça o build
COPY src /app/src
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
# fazer o build do back mvn clean package
COPY target/green-learning-0.0.1-SNAPSHOT.jar green-learning-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "green-learning-0.0.1-SNAPSHOT.jar"]
