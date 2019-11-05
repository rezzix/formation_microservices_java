# Objectif  
Assurer un monitoring de base grace aux outils cadvisor et protheus

## lancer promotheus sous docker
docker run -p 9090:9090 prom/prometheus

* traiter le conflit de ports

## lancer avec une configuration déjà prête sur le host :
docker run -p 9099:9090 -v /tmp/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

## Vérifier les flags disponibles
docker container exec xxxx prometheus -h

## Ajouter promotheus et cadvisor au fichier docker-compose

  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
    - 6080:9090
    command:
    - --config.file=/etc/prometheus/prometheus.yml
    volumes:
    - ./prometheus.yml:/etc/prometheus/prometheus.yml:ro
    depends_on:
    - cadvisor
  cadvisor:
    image: google/cadvisor:latest
    container_name: cadvisor
    ports:
    - 7000:8080
    volumes:
    - /:/rootfs:ro
    - /var/run:/var/run:rw
    - /sys:/sys:ro
    - /var/lib/docker/:/var/lib/docker:ro
    depends_on: springbootapp
    
## configurer prometheus : prometheus.yml
scrape_configs:
- job_name: cadvisor
  scrape_interval: 5s
  static_configs:
  - targets:
    - cadvisor:7000
    
## vérifier l'accès sur cadvisor 
Ouvrir le port 7000 sur le navigateur
Explorer les performances des conteneurs


## vérifier l'accès sur prometheus
Ouvrir le port 6080 sur le navigateur.