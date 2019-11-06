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
    url: jdbc:postgresql://localhost:5432/sales
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

## lancer le frontal nginx 

Utiliser la configuration 
```
server {
    listen 80;
    charset utf-8;
    access_log off;

    location / {
        proxy_pass http://10.x.x.x:9090;
        proxy_set_header Host $host:$server_port;
        #proxy_set_header X-Forwarded-Host $server_name;
        #proxy_set_header X-Real-IP $remote_addr;
        #proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    location /static {
        access_log   off;
        expires      30d;

        alias /app/static;
    }
}
```

Ajouter un service au fichier docker-compose.yml

```yaml
  nginx:
    container_name: v-nginx
    image: nginx:1.13
    restart: always
    ports:
    - 1180:80
    - 443:443
    volumes:
    - ./nginx/conf.d:/etc/nginx/conf.d
    depends_on:
    - springbootapp
```
## Facultatif : configurer l'image nginx pour être un proxy  sur les deux instances
* Configurer une deuxième instance du microservice (en changeant le port)
* Remplacer le proxy direct par un proxy sur un cluster :
  - proxy_pass  http://10.x.x.x:9090; par proxy_pass http://appcluster;
  - définir le cluster 
```  
  upstream appcluster {
    least_conn;
    server 10.x.x.x:9090;
    server 10.x.x.x:9091;
    queue 100 timeout=70;
  }
```   