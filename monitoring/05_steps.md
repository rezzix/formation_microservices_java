# Objectif  
protheus

## lancer promotheus sous docker
docker run -p 9090:9090 prom/prometheus

* traiter le conflit de ports

## lancer avec une configuration déjà prête sur le host :
docker run -p 9099:9090 -v /tmp/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

## Vérifier les flags disponibles
docker container exec xxxx prometheus -h
