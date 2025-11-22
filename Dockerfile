# ---- Stage 1: Build ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven files first to leverage caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies only (caches unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the app (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the app
CMD ["java", "-jar", "app.jar"]
