FROM node:13-alpine
WORKDIR /app
COPY package*.json ./
RUN npm i --production
COPY . .
EXPOSE 5000
CMD [ "node", "index.js"]
