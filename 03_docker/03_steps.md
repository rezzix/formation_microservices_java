# Commandes de base
## Sur https://labs.play-with-docker.com
### Faire un tour de l'environnement :
```bash
> uname -a
> ip a
> df -h
> top
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
docker container start xyz
docker container exec xyz ls
docker container exec -it xyz vi

### Creation d'un conteneur java

```bash
#> docker run -it --name mymicroservice openjdk:8-jdk-alpine /bin/sh
docker#> apk update
docker#> apk add figlet
docker#> java -version

```

### Lancement du microservice packagé
```bash
docker#> apk get wget
docker#> mkdir /usr/local/microservice
docker#> cd /usr/local/microservice
docker#> wget www.mederp.net/downloads/02_springboot-2.1.2.RELEASE.jar
docker#> java -jar 02_springboot-2.1.2.RELEASE.jar
```

### modification du port d'écoute
```bash
#> docker run -it openjdk:8-jdk-alpine /bin/sh
```

### externalisation du dossier de l'application
```bash
#> docker run -v /tmp:/usr/local/microservice -it --name mymicroservice2 openjdk:8-jdk-alpine /bin/sh
```