FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
RUN mkdir /FilesFolder
COPY /target/CloudStorage-0.0.1-SNAPSHOT.jar cs.jar
CMD ["java", "-jar", "cs.jar"]
