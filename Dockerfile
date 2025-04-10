# Etapa 1: Construção do JAR com Maven
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

# Copie o arquivo pom.xml e baixe as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o código-fonte e faça o build
COPY src /app/src

# Construa o projeto com Maven e gere o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Deploy da aplicação com OpenJDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copie o arquivo JAR gerado da etapa anterior
COPY --from=build /app/target/green-learning-0.0.1-SNAPSHOT.jar green-learning-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "green-learning-0.0.1-SNAPSHOT.jar"]
