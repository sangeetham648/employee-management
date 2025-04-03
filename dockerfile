# Use an official OpenJDK runtime as a parent image
# FROM maven:3.9.6-openjdk-17 AS build
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set the working directory for the build stage
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline


# Copy the source code and Build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use a smaller JDK base image for the final stage
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=build /app/target/employee-management-0.0.1-SNAPSHOT.jar .

# Expose the port that the application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/employee-management-0.0.1-SNAPSHOT.jar"]
