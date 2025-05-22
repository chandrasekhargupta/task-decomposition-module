# Use official OpenJDK 21 JRE image as the base image
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your build output (adjust the name if needed)
COPY target/task-decomposition-1.0.0.jar app.jar

# Expose the port your Spring Boot app runs on (default 3001 based on your logs)
EXPOSE 3001

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]