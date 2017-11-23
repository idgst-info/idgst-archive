FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY ./target/universal/idgst*.zip $PROJECT_HOME

WORKDIR $PROJECT_HOME

RUN unzip $PROJECT_HOME/idgst*.zip
RUN rm $PROJECT_HOME/idgst*.zip
RUN mv idgst* idgst

# production.conf if being added in docker compose file
CMD [ "./idgst/bin/idgst", "-Dconfig.file=/opt/app/config/production.conf" ]