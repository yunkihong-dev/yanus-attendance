# jdk11 Image Start
FROM openjdk:11

# Argument settings - JAR_File
ARG JAR_FILE=build/libs/*.jar

# Clone jar file
COPY ${JAR_FILE} app.jar

# Copy adminpage.js to the image
COPY path/to/adminpage.js /path/in/container/adminpage.js

# Run command
ENTRYPOINT ["java", "-jar", "app.jar"]
