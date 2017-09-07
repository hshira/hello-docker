start=`date +%s`
#!/bin/bash
SERVICE_NAME=hello-docker-service
TAG=latest
DOCKER_COMPOSE_FILE_NAME=docker-compose.yml

docker-compose -f ${DOCKER_COMPOSE_FILE_NAME} stop

if [[ $1 != "redeploy" ]]
then
    mvn clean install -DskipTests=true
    docker-compose -f ${DOCKER_COMPOSE_FILE_NAME} build
fi
docker-compose -f ${DOCKER_COMPOSE_FILE_NAME} up -d --force-recreate

rm -rf ./logs/*
sleep 10

/usr/bin/open -a "/Applications/Google Chrome.app" "http://localhost:8080"
tail -1000f ./logs/${SERVICE_NAME}.log
