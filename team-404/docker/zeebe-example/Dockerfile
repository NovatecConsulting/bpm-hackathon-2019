FROM maven:3-jdk-8 as builder
VOLUME /tmp
WORKDIR /workspace
COPY zeebe-example/pom.xml .
COPY zeebe-example/src ./src
RUN mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /workspace/target/zeebe-example-0.0.1-SNAPSHOT.jar /zeebe-example.jar
CMD ["java", "-jar", "zeebe-example.jar"]