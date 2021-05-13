FROM maven:3.6.3-jdk-11 AS maven
COPY . .
RUN mvn clean install
WORKDIR /target
ENTRYPOINT ["java","-jar","msnotification-1.0.0.jar"]
