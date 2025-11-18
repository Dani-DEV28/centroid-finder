#Base image (java + maven)
FROM maven:3.9.8-eclipse-temurin-21 AS build-env

# Install Node.js
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

# Set working dir
WORKDIR /app

# Copy project files
COPY . .

# Build Java processor job
WORKDIR /app/processor
RUN mvn clean package -DskipTests

# Build Node.js API
WORKDIR /app/server
RUN npm install --production

# Final runtime image
FROM eclipse-temurin:21-jdk

# Copy built artifacts
WORKDIR /app
COPY --from=build-env /app/processor/target/*.jar batch.jar
COPY --from=build-env /app/server server

# Run both Java + Node
CMD ["bash", "-c", "java -jar batch.jar & node server/index.js"]