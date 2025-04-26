# 1. Sử dụng JDK nhẹ, phù hợp với runtime
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2.
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build app/target/HotelBooking-0.0.1-SNAPSHOT.war app.war
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.war"]
