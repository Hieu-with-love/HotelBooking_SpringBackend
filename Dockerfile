# 1. Sử dụng JDK nhẹ, phù hợp với runtime
FROM maven:3.9.9-eclipse-temurin-17-focal AS build
COPY . .
RUN mvn clean package -DskipTests

# 2.
FROM eclipse-temurin:17-alpine
COPY --from=build /target/HotelBooking-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]

