# ============================
# 1. Build Java JAR with Maven
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS build-java
WORKDIR /processor

# Copy processor module
COPY processor/pom.xml .
RUN mvn dependency:go-offline

COPY processor/src ./src
RUN mvn clean package -DskipTests

# ============================
# 2. Final Runtime Container
# ============================
FROM eclipse-temurin:23-jre AS runtime

# Install Node 25 + ffmpeg + bash
# Debian-based since Temurin uses Ubuntu/Debian
RUN apt-get update && \
    apt-get install -y curl ffmpeg bash && \
    curl -fsSL https://deb.nodesource.com/setup_25.x | bash - && \
    apt-get install -y nodejs && \
    apt-get clean

# Install Java (required to run the JAR)
# RUN apk add --no-cache openjdk21-jre ffmpeg bash

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