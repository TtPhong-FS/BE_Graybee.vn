FROM openjdk:17-slim

# Đặt thư mục làm việc
WORKDIR /app

COPY target/ecommerce-graybee-server.jar ecommerce-graybee-server.jar

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx2048m", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "ecommerce-graybee-server.jar"]
