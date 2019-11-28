# Monitoring

## Start:
docker-compose up

## Start Application
cd data-app && mvn clean install && mvn spring-boot:run

## Generate Data
./generate-data.sh
Cancel with Ctrl-C when you have enough data

## Kibana:
http://localhost:5601/

## Stop:
docker-compose down
