# Sur l'éditeur Swagger

## Lancer l'editeur Swagger :
Il est possible de lancer l'éditeur swagger de plusieurs manières :
* Sur le site swagger :
https://app.swaggerhub.com (enregistrement gratuit pour une instance virtuelle en cloud)

* En local ou sur docker :
docker run -d -p 80:8080  swaggerapi/swagger-editor

* Sur play (même commande)
https://labs.play-with-docker.com

Supprimer le contenu par défaut.

Copier et completer la définition des services en respectant l'ordre :
1. ventes_info
2. ventes_paths
3. ventes_servers
4. ventes components 

Générer différents serveurs basés sur la définition de l'API, nous retenons l'implémentation serveur jersey pour la suite de l'exercice.

# Sur Eclipse
## importer le projet maven
Nouveau projet maven sans archetype
Copier le code généré (par Swagger codegen) dessus en écrasant les ficher déjà générés par Eclipse


## lancer le projet
Vérifier le fichier pom.xml
- les dépendances
- les ports de lancement de jetty
Utiliser les paramètres du build :
* package
* jetty:run

## Pour eviter le conflit de ports
remplacer 8080 par 18080 sur pom.xml

## Ouvrir le fichier de description openapi
http://localhost:18080/ventes_v1/openapi.json

## Ouvrir le descripteur de déploiement web.xml
Activer la génération du fichier de description WADL sur web.xml
```xml
        <init-param>
            <param-name>jersey.config.server.wadl.disableWadl</param-name>
            <param-value>false</param-value>
        </init-param>
```  
http://localhost:18080/ventes_v1/application.wadl

## Personnaliser les retours des méthodes métier implémentés
### Utiliser les code snippets
Utiliser les classes Customer, Sale et ClientDb pour simuler un système :
- Ajouter les dossier src/main et src/gen au build path
- Modifier les packages des classes importées.
- Implémenter les méthodes générées en retournant :
``` java
  * Response.ok().entity(XXX).build() pour les objets
  * Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "mon message")).build() pour les messages.
```
## Editer les fonctions des services et relancer
Retourner un client à partir de ClientsDB en utilisant le get (avec son id en paramètre).
Retourner une liste de clients à partir de ClientsDB en utilisant getValues()

# Sur SoapUI
## Creer un projet REST à partir du fichier wadl généré
* Créer un nouveau projet, choisir une définition wadl au démarrage ou importer après création:
http://localhost:18080/ventes_v1/application.wadl
* Vérifier les requêtes générées
* Personnaliser les requêtes pour afficher les clients programmés dans ClientsDB

# Troubleshooting
1. Problèmes de syntaxe sur l'editeur swagger codegen
  * Vérifier le niveau de tabulation
  * Prendre en considération les astuces proposées par l'éditeur
2. Problèmes sur le lancement du code généré
  * Vérifier le mapping de la servlet swagger sur web.xml
  * Vérifier les ports utilisés dans pom.xml
3. Problèmes sur requêtes de test
  * Vérifier s'il y a des loupés comme des '//' au lieu de '/' dans les adresses