FROM maven:3-jdk-8 as builder
VOLUME /tmp
WORKDIR /workspace
COPY schaden-berechnungs-service/pom.xml .
COPY schaden-berechnungs-service/src ./src
RUN mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /workspace/target/schaden-berechnungs-service-0.0.1-SNAPSHOT.jar /service.jar
CMD ["java", "-jar", "service.jar", "-Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"]