# =========================
# Build Stage (Java + Node)
# =========================
FROM maven:3.9.8-eclipse-temurin-21 AS build-env

# Set working directory
WORKDIR /app

# Copy Java processor module
COPY processor /app/processor

# Build Java batch job (skip tests to speed up build)
WORKDIR /app/processor
RUN mvn clean package -DskipTests

# Copy Node API
COPY server /app/server

# Install Node dependencies for production
# Note: Node is not installed here yet, but we can still copy package.json for caching
WORKDIR /app/server
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && npm install --production

# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:21-jdk

# Install Node.js in the final runtime image
RUN apt-get update && apt-get install -y curl gnupg ca-certificates \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

# Set working directory
WORKDIR /app

# Copy Java JAR from build stage
COPY --from=build-env /app/processor/target/*.jar batch.jar

# Copy Node API from build stage
COPY --from=build-env /app/server server

# Install Node dependencies in final image
WORKDIR /app/server
RUN npm install --production

# Run both Java batch job and Node API
# Java runs in background (&), Node runs in foreground
CMD ["bash", "-c", "java -jar ../batch.jar & node index.js"]