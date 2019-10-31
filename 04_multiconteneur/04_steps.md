# Objectif  
backend Spring boot
datastore Postgres
frontal nginx

## modification du microservice
Ajouter la dépendance postgres :
```xml
	<dependency>
		<groupId>org.postgresql</groupId>
		<artifactId>postgresql</artifactId>
		<scope>runtime</scope>
	</dependency>
```
Ajouter un nouveau profile dans la config yml avec les différences suivantes
```yaml
      dialect: org.hibernate.dialect.PostgreSQLDialect
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
    driverClassName: org.postgresql.Driver
```

tester sur eclipse avec le profile dev en ajoutant le paramètre JVM :
-Dspring.profiles.active=dev

Modifier la version de l'appli Spring boot et regénerer le war 

## mettre à jour l'image

## faire un push sur docker hub

## lancer le conteneur postgres
docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v /var/postgres:/var/lib/postgresql/data  postgres

## mettre le tout sur un fichier docker-compose
```
version: "3"
services:
  postgres:
    image: postgres:latest
    network_mode: bridge
    container_name: postgres
    volumes:
      - postgres-data:/var/lib/postgresql/data
    expose:
    - 5432
    ports:
      - 5432:5432
    environment:
         - POSTGRES_PASSWORD=postgres
         - POSTGRES_USER=postgres
         - POSTGRES_DB=sales
    restart: unless-stopped
# APP*****************************************
  springbootapp:
    image: formation/microservice:V3
    network_mode: bridge
    container_name: sales_microservice
    expose:
      - 9090
    ports:
      - 9090:9090
    restart: unless-stopped
    depends_on:
      - postgres
    links:
      - postgres
volumes:
  postgres-data:
```
## Tester et vérifier les données
Tester à l'aide de postman et vérifier les données à l'aide 

## Acceder à la base postres et vérifier les données
executer la commande psql 
* #>psql -U postgres : se connecter au serveur
* postgres>\l : lister les base de données
* postgres>\c sales; : se connecter à la BD
* postgres>\dt : lister les tables 
* postgres>select * .... ; : executer des requêtes SQL
* postgres>\q : quitter

## lancer deux instances du conteneur
à partir de l'image du microservice

## configurer l'image nginx pour être un proxy HA sur les deux instances

## lancer le frontal nginx 
