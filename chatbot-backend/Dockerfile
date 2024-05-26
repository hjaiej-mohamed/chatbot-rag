# Use Maven image with OpenJDK 20 to build the project
FROM jelastic/maven:3.9.5-openjdk-20.0.2 AS builder
WORKDIR /app

# Copy the POM file and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Use a slim version of OpenJDK 20 for running the application
FROM openjdk:20-jdk-slim
ENV APP_HOME=/usr/src/app
WORKDIR $APP_HOME

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/chatbot-0.0.1-SNAPSHOT.jar /chatbot-0.0.1-SNAPSHOT.jar

EXPOSE 8082

# Set the entry point to run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/chatbot-0.0.1-SNAPSHOT.jar"]
