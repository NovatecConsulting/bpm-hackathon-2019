FROM maven:3-jdk-8 as builder
VOLUME /tmp
WORKDIR /workspace
COPY kafka-example/pom.xml .
COPY kafka-example/src ./src
RUN mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /workspace/target/kafka-example-0.0.1-SNAPSHOT.jar /kafka-example.jar
CMD ["java", "-jar", "kafka-example.jar"]