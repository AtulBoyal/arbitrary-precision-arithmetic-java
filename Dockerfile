# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/aarithmetic.jar aarithmetic.jar
ENTRYPOINT ["java", "-jar", "aarithmetic.jar"]
CMD ["int", "add", "0", "0"]
