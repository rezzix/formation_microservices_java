# Objectif  
frontal Nginx
backend Spring boot
datastore Postgres

## modification du microservice
Ajouter la dépendance postgres :
```xml
	<dependency>
		<groupId>org.postgresql</groupId>
		<artifactId>postgresql</artifactId>
		<scope>runtime</scope>
	</dependency>
```
Ajouter un nouveau profile dans la config yml
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

## mettre à jour l'image

## faire un push sur docker hub

## lancer le conteneur postgres
docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v /var/postgres:/var/lib/postgresql/data  postgres

## lancer deux instances du conteneur
à partir de l'image du microservice

## configurer l'image nginx pour être un proxy HA sur les deux instances

## lancer le frontal nginx 

## mettre le tout sur un fichier docker-compose
```
version: '3'
services:
  nginx:
   container_name: nginx
   image: nginx:1.13
   restart: always
   ports:
   - 80:80
   - 443:443
   volumes:
   - ./nginx/conf.d:/etc/nginx/conf.d
    depends_on:
    - mymicroservice1, mymicroservice2
  microservice1
    container_name: microservice1
    image: username/imagename
    ports:
    - 9090:9090
    volumes:
    - /tmp:/opt/microservice
  postgres:
    container_name: postgres
    image: postgres
    environment:
     POSTGRES_PASSWORD: postgres
    volumes :
    - /var/postgres:/var/lib/postgresql/data
   ports:
   - "5432:5432"
   restart: always
```