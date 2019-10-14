# Sur l'éditeur Swagger

## lancer l'editeur swagger sur docker
docker run -d -p 80:8080  swaggerapi/swagger-editor 


# Sur Eclipse
## importer le projet maven
Nouveau projet maven sans archetype
Copier le code généré (par Swagger codegen) dessus en écrasant les ficher déjà générés par Eclipse


## lancer le projet
Utiliser les paramètres du build :
* package
* jetty:run

## Ouvrir le fichier de description openapi
http://localhost:8080/ventes_v1/openapi.json

## Ouvrir le fichier de description
Activer la génération du fichier de description WADL sur web.xml
        <init-param>
            <param-name>jersey.config.server.wadl.disableWadl</param-name>
            <param-value>**true**</param-value>
        </init-param>
        
http://localhost:8080/ventes_v1/application.wadl

## Personnaliser les retours des méthodes métier implémentés
### Utiliser les code snippets
Utiliser les classes Customer, Sale et ClientDb pour simuler un système

# Sur SoapUI
## Creer un projet REST à partir du fichier wadl généré
* Créer un nouveau projet, choisir une dédinition wadl au démarrage ou importer après création:
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