FROM openjdk:8-jdk-alpine
RUN mkdir /app
COPY target/product.jar /app
WORKDIR /app
EXPOSE 3000
# ENTRYPOINT ["java","-jar","product.jar"]