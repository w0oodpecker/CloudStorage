FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
RUN mkdir /FilesFolder
RUN mkdir /logs
COPY /target/CloudStorage-0.0.1-SNAPSHOT.jar cs.jar
CMD ["java", "-jar", "cs.jar"]
