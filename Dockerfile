FROM amazoncorretto:17
LABEL maintainer="jsm5315@ajou.ac.kr"

ARG JAR_FILE=build/libs/test-0.0.1-SNAPSHOT.jar

WORKDIR /home/java/SimpleFileUploader

COPY ${JAR_FILE} /home/java/SimpleFileUploader/file-uploader.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=release","/home/java/SimpleFileUploader/file-uploader.jar"]