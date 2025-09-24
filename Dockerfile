FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/CollageBackend.jar CollageBackend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "CollageBackend.jar"]