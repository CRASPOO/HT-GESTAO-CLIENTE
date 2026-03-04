# Estágio 1: Build (JDK 21 é obrigatório aqui)
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o pom.xml e baixa as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o .jar
COPY src ./src
RUN mvn package -DskipTests

# Estágio 2: Runtime (JRE 21 é suficiente e mais leve)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o jar gerado (o nome do artefato vem do seu pom.xml: cardapio-0.0.1-SNAPSHOT.jar)
COPY --from=builder /app/target/cliente-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]