from composer:2.3.10 as builder
WORKDIR /app/
COPY ./ /app/
RUN composer update
RUN composer install


FROM php:8.1-fpm

WORKDIR /app
COPY --from=builder /app/vendor /app/vendor
# install mysql driver
RUN docker-php-ext-install mysqli pdo pdo_mysql

COPY ./ /app/

# permision fix
RUN sed -ri 's/^www-data:x:82:82:/www-data:x:1000:50:/' /etc/passwd
RUN chown -R www-data:www-data /app
