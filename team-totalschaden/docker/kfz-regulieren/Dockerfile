FROM maven:3-jdk-8 as builder
VOLUME /tmp
WORKDIR /workspace
COPY kfz-schaden-regulierung/pom.xml .
COPY kfz-schaden-regulierung/src ./src
RUN mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /workspace/target/kfz-schaden-regulierung-0.0.1-SNAPSHOT.jar /service.jar
CMD ["java", "-jar", "service.jar"]