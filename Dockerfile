#Base image (java + maven)
FROM maven:3.9.8-eclipse-temurin-21 AS build-env


# Set working dir
WORKDIR /app

# Install Node.js in runtime image
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

# Copy Java processor module
COPY processor /app/processor
COPY server /app/server

# Build Java processor job
WORKDIR /app/processor
RUN mvn clean package -DskipTests

# # Copy Node API
# COPY server /app/server

# Build Node.js API
WORKDIR /app/server
RUN npm install --production

# Final runtime image
FROM eclipse-temurin:21-jdk

# Install node & ffmpeg in final image
RUN apt-get update && apt-get install -y curl ca-certificates ffmpeg \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy Java JAR and Node API from build stage
COPY --from=build-env /app/processor/target/*.jar /app/batch.jar
COPY --from=build-env /app/server /app/server

# # Copy a small start script that launches both services and forwards signals
# COPY start.sh /app/start.sh
# RUN chmod +x /app/start.sh

# Default envs (override at runtime)
ENV PORT=3000
ENV JAR_PATH=/app/batch.jar
# Do not hardcode VIDEOS_DIR/RESULTS_DIR here; pass via -e or a mounted .env if needed

EXPOSE 3000

CMD ["sh","-c","set -euo pipefail; \
  java -jar \"${JAR_PATH:-/app/batch.jar}\" & JAVA_PID=$!; \
  node /app/server/index.js & NODE_PID=$!; \
  term() { echo 'Shutting down'; kill -TERM \"$NODE_PID\" 2>/dev/null || true; kill -TERM \"$JAVA_PID\" 2>/dev/null || true; wait; }; \
  trap term SIGTERM SIGINT; \
  wait -n; term; sleep 1"]