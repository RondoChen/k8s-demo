# FROM golang:alpine

# WORKDIR /app
# COPY build/. /app

# EXPOSE 6000
# CMD ["./main"]


# build
FROM golang:alpine AS builder
ADD . /src
RUN cd /src && go build -o main

# runtime
FROM alpine
WORKDIR /app
COPY --from=builder /src/main /app/
COPY ./config/config.yaml /app/config/config.yaml
EXPOSE 6000

