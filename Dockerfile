# FROM eclipse-temurin:25-jdk
# WORKDIR /app

# # Copy Maven wrapper dan pom.xml supaya bisa resolve dependencies
# COPY .mvn/ .mvn/
# COPY mvnw pom.xml ./

# # Download dependencies dulu
# RUN ./mvnw dependency:go-offline

# # Source code akan di-mount dari host
# CMD ["./mvnw", "spring-boot:run"]

# Stage 1: build JAR dengan Maven
FROM eclipse-temurin:25-jdk AS build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run JAR
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
