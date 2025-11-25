FROM node:20-alpine
WORKDIR /app

# Install Java so Node can spawn it
RUN apk add --no-cache openjdk17-jre

# Declare default envs so app has sane defaults inside container
ENV VIDEO_DIR=/videos
ENV RESULTS_DIR=/results
ENV PORT=3000
ENV JAR_PATH=app.jar

# indicate intended mount points (does not bind host paths)
VOLUME ["/videos", "/results"]

# copy package files
COPY package*.json ./

# install only production deps
RUN npm install --production

# copy source code
COPY . .

# Copy final Java JAR from processor build folder
COPY ../processor/target/centroid-finder-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# expose express port
EXPOSE 3000

CMD ["npm", "start"]
