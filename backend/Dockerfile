# ============================================================
# STAGE 1: Build (Maven + JDK 17)
# ============================================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Koobiye pom.xml marka hore si Docker cache-ku u dependencies-ka
# (wuu ka dhigayaa rebuild-yada ku xiga mid degdeg badan)
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ============================================================
# STAGE 2: Run (JRE kaliya - image-ka ugu dambeeya wuu yaraan)
# ============================================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
