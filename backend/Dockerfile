# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-oraclelinux8

# Set the working directory inside the container
WORKDIR /myworld-backend

# Copy the application's JAR file to the container
COPY target/myworld-api-0.0.1-SNAPSHOT.jar backend.jar

# Expose the port that the application will run on
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "backend.jar"]