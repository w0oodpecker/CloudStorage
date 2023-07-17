FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
COPY /target/CloudeStorage-0.0.1-SNAPSHOT.jar CloudStorage.jar
CMD ["java", "-jar", "CloudStorage.jar"]
