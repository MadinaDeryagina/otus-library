FROM openjdk:8u102-jre
ARG finalName
ADD target/$finalName /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]