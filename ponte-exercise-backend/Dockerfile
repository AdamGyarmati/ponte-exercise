FROM openjdk:17-jdk-slim

COPY . /app

WORKDIR /app

RUN ./mvnw clean package -Dmaven.test.skip

CMD ["java", "-jar", "target/ponte-exercise-backend-0.0.1-SNAPSHOT.jar"]