# Use an official JDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file to the container
COPY target/mqtransition-0.0.10-SNAPSHOT.jar mqtransition.jar

# Expose the application port
EXPOSE 8081

# Command to run the application with optional arguments
ENTRYPOINT ["java", "-jar", "mqtransition.jar"]
