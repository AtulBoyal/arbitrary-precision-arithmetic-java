# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Package stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/aarithmetic.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["int", "add", "0", "0"]
