# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set volume (optional for logs)
VOLUME /tmp

# Set environment variable (optional)
ENV JAVA_OPTS=""

# Copy the jar file from target to app.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Start the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
