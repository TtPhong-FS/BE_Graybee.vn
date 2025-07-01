FROM maven:3.9.6-eclipse-temurin-17 AS build
# Đặt thư mục làm việc
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim
# Đặt thư mục làm việc
WORKDIR /app

COPY --from=build /app/target/techstore-server.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx2048m", "-jar", "/app/techstore-server.jar"]
