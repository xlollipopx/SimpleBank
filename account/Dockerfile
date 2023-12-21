FROM openjdk:17-jdk-slim

MAINTAINER anton.stelmax.com

#Add application jar to the image
COPY build/libs/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java", "-jar", "account-0.0.1-SNAPSHOT.jar"]