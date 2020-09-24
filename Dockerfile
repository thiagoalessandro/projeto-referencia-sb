FROM openjdk:8-jdk-alpine

LABEL maintainer="thiago.alessandro.farias@gmail.com"

ENV TZ=America/Belem
ENV APP_FILE=projeto-referencia-sb.jar
ENV APP_DIR=/tmp/app/

VOLUME /tmp

EXPOSE 8080

ADD target/${APP_FILE} ${APP_DIR}/${APP_FILE}

ENTRYPOINT java -jar ${APP_DIR}/${APP_FILE}
