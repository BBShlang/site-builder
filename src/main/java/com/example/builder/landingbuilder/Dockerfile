# === STAGE 1: build ===
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package

# === STAGE 2: run ===
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# имя jar — из лога: landing-builder-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/landing-builder-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
