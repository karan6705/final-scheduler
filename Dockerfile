# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Set working directory to the backend
WORKDIR /app/Backend-SpringBoot/exam-scheduler

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/exam-scheduler-0.0.1-SNAPSHOT.jar"] 