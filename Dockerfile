FROM openjdk:8

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY ./target/universal/idgst*.zip $PROJECT_HOME

WORKDIR $PROJECT_HOME

RUN unzip $PROJECT_HOME/idgst*.zip -d idgst
RUN rm $PROJECT_HOME/idgst*.zip