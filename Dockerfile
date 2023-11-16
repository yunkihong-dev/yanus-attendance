# jdk11 Image Start
FROM openjdk:11

# Argument settings - JAR_File
ARG JAR_FILE=build/libs/*.jar

# Clone jar file
COPY ${JAR_FILE} app.jar

# You can proceed by combining the parameter setting part and the jar file replication part.
# COPY build/libs/*.jar app.jar

# Run command
ENTRYPOINT ["java", "-jar", "app.jar"]
