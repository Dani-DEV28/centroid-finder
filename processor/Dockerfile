# ---- Build with Maven ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# copy the pom.xml and download dependencies first (caches)
COPY pom.xml .
RUN mvn dependency:go-offline

# now copy the source and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy the final jar from build stage (adjust target/*.jar name)
# COPY --from=build /app/target/*.jar app.jar

COPY --from=build /app/target/centroid-finder-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# expose API port
EXPOSE 8080

# run app
ENTRYPOINT ["java", "-jar", "app.jar"]
