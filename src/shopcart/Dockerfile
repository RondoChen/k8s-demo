# build
FROM golang:alpine3.15 AS builder
# ARG GOPROXY=https://goproxy.cn,direct
WORKDIR /src
COPY go.mod .
COPY go.sum .
RUN go mod download -x
COPY . /src
RUN go build -o main -x

# runtime
FROM alpine:3.17
WORKDIR /app
COPY --from=builder /src/main /app/
COPY ./config/config.yaml /app/config/config.yaml
EXPOSE 6000

