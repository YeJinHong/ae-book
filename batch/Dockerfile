FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} batch.jar
EXPOSE 8084
ENTRYPOINT java -jar /batch.jar
