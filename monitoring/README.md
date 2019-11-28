# BPM Hackathoon 2019
# Team: Monitoring

## Start:
docker-compose build    
docker-compose up

## Start Application
cd data-app && mvn clean install && mvn spring-boot:run

## Generate Data
./generate-data.sh  
Cancel with Ctrl-C when you have enough data

## Access-Information
## App(Camunda):
http://localhost:8080/  
user: demo  
pw  : demo  

## PSQL DB:
http://localhost:5432/  
user: demo  
pw  : demo

## Logstash 
http://localhost:9600/  
user: elastic   
pw  : demo

## ElasticSearch
http://localhost:9200/  
user: elastic   
pw  : demo

## Kibana:
http://localhost:5601/  
user: elastic   
pw  : demo

## Stop:
docker-compose down
