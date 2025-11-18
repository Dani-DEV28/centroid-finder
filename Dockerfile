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

# Copy Node API
COPY server /app/server

# Build Node.js API
WORKDIR /app/server
RUN npm install --production

# Final runtime image
FROM eclipse-temurin:21-jdk


# Set working directory
WORKDIR /app

# Copy Java JAR from build stage
COPY --from=build-env /app/processor/target/*.jar batch.jar

# Copy Node API from build stage
COPY --from=build-env /app/server server

# Run both Java batch job + Node API
CMD ["bash", "-c", "java -jar batch.jar & node server/index.js"]