FROM postgres:latest

RUN apt-get update && \
    apt-get -y install postgresql-14-cron && \
    apt install nano && \
    apt-get -y install sudo

WORKDIR /scripts

#USER postgres

COPY pg_config.sh .

RUN chmod +x ./pg_config.sh \
    ./pg_config.sh \