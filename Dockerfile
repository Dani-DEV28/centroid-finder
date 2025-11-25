# ============================
# 1. Build Java JAR with Maven
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build-java
WORKDIR /processor

# Copy processor module
COPY processor/pom.xml .
RUN mvn dependency:go-offline

COPY processor/src ./src
RUN mvn clean package -DskipTests

# ============================
# 2. Final Runtime Container
# ============================
FROM node:20-alpine

# Install Java (required to run the JAR)
RUN apk add --no-cache openjdk17-jre ffmpeg bash

WORKDIR /app

# ----------------------------
# Environment defaults
# ----------------------------
ENV VIDEO_DIR=/videos \
    RESULTS_DIR=/results \
    PORT=3000 \
    JAR_PATH=/app/app.jar

VOLUME ["/videos", "/results"]

# ----------------------------
# Install Node dependencies
# ----------------------------
COPY server/package*.json ./
RUN npm ci --omit=dev

# Copy Node server code
COPY server/. .

# ----------------------------
# Copy Java JAR built in stage 1
# ----------------------------
COPY --from=build-java /processor/target/centroid-finder-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

# ----------------------------
# Expose Node API
# ----------------------------
EXPOSE 3000

CMD ["npm", "start"]