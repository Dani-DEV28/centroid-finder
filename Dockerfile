# ...existing code...
# Multi-stage build: build Java processor and Node server, produce runtime image
FROM maven:3.9.8-eclipse-temurin-21 AS build-env

WORKDIR /app

# Install node + ffmpeg in build stage so we can run npm if needed
RUN apt-get update && apt-get install -y curl ffmpeg ca-certificates \
  && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
  && apt-get install -y nodejs \
  && rm -rf /var/lib/apt/lists/*

# Copy and build Java processor
COPY processor /app/processor
WORKDIR /app/processor
RUN mvn clean package -DskipTests

# Copy server and install production deps
COPY server /app/server
WORKDIR /app/server
RUN npm ci --production

# Final runtime image
FROM eclipse-temurin:21-jdk

# Install node + ffmpeg in final image (needed by fluent-ffmpeg)
RUN apt-get update && apt-get install -y curl ffmpeg ca-certificates \
  && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
  && apt-get install -y nodejs \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy built JAR and server from build stage
COPY --from=build-env /app/processor/target/centroid-finder-1.0-SNAPSHOT-jar-with-dependencies.jar /app/batch.jar
COPY --from=build-env /app/server /app/server

# Expose API port and sensible defaults
ENV PORT=3000
ENV JAR_PATH=/app/batch.jar
EXPOSE 3000

# Default: run only the Node API (change to run the JAR once you have a runnable jar)
# CMD ["node", "/app/server/index.js"]

# If you want to run both Java and Node from this image (less robust),
# replace the CMD above with:
CMD ["sh","-c","java -jar \"${JAR_PATH}\" & node /app/server/index.js"]
# ...existing code...