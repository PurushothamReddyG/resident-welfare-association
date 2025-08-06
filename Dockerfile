# Use lightweight JDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy your specific JAR into the container
COPY target/resident-welfare-association-0.0.1-SNAPSHOT.jar app.jar

# Set entrypoint to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
