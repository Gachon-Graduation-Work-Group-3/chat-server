FROM openjdk:21
ARG JAR_FILE=presentation/build/libs/presentation-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
