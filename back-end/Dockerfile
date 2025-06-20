FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/back-end-0.0.1-SNAPSHOT.jar"]
CMD []
