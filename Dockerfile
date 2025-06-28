# Temel Java image
FROM openjdk:17-jdk-slim

# Uygulama JAR dosyasını ekle
COPY target/portfolio-0.0.1-SNAPSHOT.jar app.jar

# Portu tanımla (Spring Boot default: 8080)
EXPOSE 8080

# Uygulama çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]