FROM amazoncorretto:23-alpine-jdk
ARG JAR_FILE=target/ferovinum-technical-assignment-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} ferovinum-technical-assignment.jar
ENTRYPOINT exec java --enable-preview -jar ferovinum-technical-assignment.jar