FROM node:20-alpine
WORKDIR /app

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
RUN npm install --omit=dev

# copy source code
COPY . .

# expose express port
EXPOSE 3000

CMD ["npm", "start"]
