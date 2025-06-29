## Temel Java image
#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#
## Uygulama JAR dosyasını ekle
#COPY target/portfolio-0.0.1-SNAPSHOT.jar app.jar
#
## Portu tanımla (Spring Boot default: 8080)
#EXPOSE 8080
#
## Uygulama çalıştır
#ENTRYPOINT ["java", "-jar", "app.jar"]
#CMD ["java", "-jar", "app.jar"]


FROM maven:3.9.2-eclipse-temurin-17  AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:17-slim-bullseye
WORKDIR /app
COPY --from=build /app/target/portfolio-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
