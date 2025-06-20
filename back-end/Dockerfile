# Use a JDK base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Package with Maven (skip tests to speed up build)
RUN chmod +x mvnw
RUN ./mvnw package -DskipTests

# Expose Spring Boot port
EXPOSE 8080

# Run the app (adjust .jar name if needed)
CMD java -jar target/back-end-0.0.1-SNAPSHOT.jar

