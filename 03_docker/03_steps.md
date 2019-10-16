
# Commandes de base
## Sur https://labs.play-with-> docker.com
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

Vérifier les conteneurs en exécution :
```bash
> docker container ls
> docker container ls -a
```