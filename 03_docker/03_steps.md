# Commandes de base
## Sur https://labs.play-with-docker.com
### Faire un tour de l'environnement :
```bash
> uname -a
> ip a
> df -h
> top
> docker info
```
### Tirer une première image
Tirer et lancer l'image alpine linux
```bash
> docker image pull alpine
```
Lancer quelques commandes de base
```bash
> docker container run alpine ls -l
> docker container run alpine echo "hello from alpine"
> docker container run alpine /bin/sh
> docker container run -it alpine /bin/sh
```
et dans le container executer les commandes :
```bash
/> ll
/> ps -a
```	
quitter avec CTL+D


### Relance et execution de commandes 
Vérifier les conteneurs en exécution :
```bash
> docker container ls
> docker container ls -a
```

Relancer le dernier, et executer une commande dessus :
```bash
docker container start xyz
docker container exec xyz ls
docker container exec -it xyz vi
```
### Creation d'un conteneur java

```bash
#> docker run -it --name mymicroservice openjdk:8-jdk-alpine /bin/sh
docker#> apk update
docker#> apk add figlet
docker#> java -version

```

### Lancement du microservice packagé
```bash
docker#> apk add wget
docker#> mkdir /opt/microservice
docker#> cd /opt/microservice
docker#> wget www.mederp.net/downloads/02_springboot-2.1.2.RELEASE.jar
docker#> java -jar 02_springboot-2.1.2.RELEASE.jar
```
NB : il est aussi possible de faire un drag & drop de eclipse directement sur la console play docker

Essayer open port pour accéder à l'application : pourquoi l'application échoue ?

### Lancement d'un meilleur container
* Ajouter l'option -d (detach) pour que le container continue à s'executer en arrière plan
  * -d
* Ajouter l'option -p (port) pour exposer le port applicatif
  * -p 80:9090
* externalisation du dossier de l'application
  * -v /tmp:/opt/microservice
```bash
#>docker run -p 80:9090 -v /tmp:/opt/microservice -it --name mynewmicroservice openjdk:8-jdk-alpine /bin/sh
docker#> 
```
### tester l'application
Relancer à partir de open port et tester avec postman

### créer une image du conteneur en cours d'execution
Si tout va bien commiter cette image
```bash
docker commit xxxx entreprise/testdockservice:V0.1
```

## Utiliser un fichier dockerfile
```
FROM openjdk:8-jdk-alpine
MAINTAINER Ossama Boughaba info@formation.com
RUN apk update
RUN apk add wget
RUN wget www.mederp.net/downloads/02_springboot-2.1.2.RELEASE.jar
RUN mkdir /opt/microservice
RUN cp 02_springboot-2.1.2.RELEASE.jar /opt/microservice
RUN cd /opt/microservice
WORKDIR /opt/microservice

EXPOSE 9090

CMD ["java", "-jar ", "02_springboot-2.1.2.RELEASE.jar"]
```
## construire l'image
Lancer la commande 
```
#>docker build .
```
Corriger les erreurs éventelles, une fois corrigés donner un nom significatif à l'image

```bash
docker build -t entreprise/mymicroservice:V1 .
```

## Faire un push sur docker hub
* Vérifier l'existance d'un compte dockerhub
* S'authentifier : 
  * docker login
* tagger l'image
  * docker tag xxxx username/imagename:version
* docker push
  * docker push username/imagename
  
## Tester à partir d'une nouvelle instance 
* lancer une nouvelle instance
  * docker run username/imagename
  * tester à partir du navigateur ou de postman

## Enregistrer sur le disc
docker save imagename > imagename.tar

NB : peut être restituée par : docker load --input imagename.tar
Question : avantage et inconvenients par rapport au push docker hub ?


# Troubleshooting

2. La fenêtre de tests navigateur n'est pas lancée
  * vérifier que le blockeur de popups n'est pas actif