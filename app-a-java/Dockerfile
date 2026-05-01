# Stage 1 - compile and package (skips tests; tests run in Cloud Build)
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# Stage 2 - minimal runtime image
FROM eclipse-temurin:11-jre-focal
WORKDIR /app
COPY --from=build /app/target/unit-converter-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
