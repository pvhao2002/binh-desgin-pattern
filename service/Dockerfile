# Build stage: Maven
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy pom and download dependencies
COPY pom.xml .
RUN apk add --no-cache maven && \
    mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn package -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Defaults (override via docker-compose env)
ENV SERVER_PORT=1122
EXPOSE 1122

COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
