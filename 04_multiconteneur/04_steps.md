# Objectif  
frontal Nginx
backend Spring boot
datastore Postgres

## modification du microservice
changer le driver et l'URL

## mettre à jour l'image

## faire un push sur docker hub

## lancer le conteneur postgres
docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data  postgres

## lancer deux instances du conteneur
à partir de l'image du microservice

## configurer l'image nginx pour être un proxy HA sur les deux instances

## lancer le frontal nginx 

## mettre le tout sur un fichier docker-compose