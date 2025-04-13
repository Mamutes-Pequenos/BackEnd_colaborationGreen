FROM maven:3.8-openjdk-17
WORKDIR /app

# Copiar todos os arquivos para o container
COPY . .

# Fazer o build do backend, ignorando os testes
RUN mvn clean package -DskipTests

# Expôr a porta 8080
EXPOSE 8080

# Definir o comando de execução do JAR
ENTRYPOINT ["java", "-jar", "/app/target/green-learning-0.0.1-SNAPSHOT.jar"]
