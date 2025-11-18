FROM node:20-alpine
WORKDIR /app

# copy package files
COPY package*.json ./

# install only production deps
RUN npm install --omit=dev

# copy source code
COPY . .

# expose express port
EXPOSE 3000

CMD ["npm", "start"]
