FROM openjdk:17-slim

# Đặt thư mục làm việc
WORKDIR /app

COPY target/ecommerce-graybee-server.jar ecommerce-graybee-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx1024m", "-Xmx2048m", "-jar", "ecommerce-graybee-server.jar"]
