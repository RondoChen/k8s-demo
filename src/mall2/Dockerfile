FROM node:19.1 as builder
ENV NODE_OPTIONS=--openssl-legacy-provider
WORKDIR	/app
COPY . /app
RUN npm install --force
RUN npm run build

FROM 

