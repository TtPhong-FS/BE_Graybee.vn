FROM openjdk:17-slim

# Đặt thư mục làm việc
WORKDIR /app

COPY target/techstore-server.jar techstore-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx2048m", "-jar", "techstore-server.jar"]
