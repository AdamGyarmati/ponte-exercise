## the base image
#FROM openjdk:17-jdk-slim
#
## the JAR file path
#ARG JAR_FILE=target/*.jar
#
## Copy the JAR file from the build context into the Docker image
#COPY ./target/ponte-exercise-backend-0.0.1-SNAPSHOT.jar /app/myapp.jar
#
#CMD apt-get update -y
#
## Set the default command to run the Java application
#ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]


# Használj egy alap Spring Boot képet
FROM openjdk:17-jdk-slim

# Másold be a projekt forrásfájljait a konténerbe
COPY . /app

# Állítsd be a munkakönyvtárat
WORKDIR /app

# Telepítsd a szükséges függőségeket és buildeld újra az alkalmazást
RUN ./mvnw clean package -Dmaven.test.skip

# Indítsd el az alkalmazást
CMD ["java", "-jar", "target/ponte-exercise-backend-0.0.1-SNAPSHOT.jar"]