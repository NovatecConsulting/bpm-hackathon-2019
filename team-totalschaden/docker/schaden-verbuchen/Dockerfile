FROM maven:3-jdk-8 as builder
VOLUME /tmp
WORKDIR /workspace
COPY schaden-verbuchen-service/pom.xml .
COPY schaden-verbuchen-service/src ./src
RUN mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /workspace/target/schaden-verbuchen-service-0.0.1-SNAPSHOT.jar /service.jar
CMD ["java", "-jar", "service.jar"]