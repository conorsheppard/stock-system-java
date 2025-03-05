FROM amazoncorretto:23-alpine-jdk
ARG JAR_FILE=target/stock-system-java-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} stock-system-java.jar
ENTRYPOINT exec java --enable-preview -jar stock-system-java.jar