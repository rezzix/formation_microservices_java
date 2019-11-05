# Sur Eclipse
## Nouveau projet maven :

Nouveau projet maven avec archetype quickstart ou sans archetype
utiliser le package com.formation.microservices
utiliser le nom d'artifact 02_springboot

## Modifier le project object model (pom.xml)
Modifier la définition du POM en ajoutant:
```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.2.RELEASE</version>
	</parent>
```

Ajouter la dépendance boot starter web:
```xml
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-web</artifactId>
	</dependency>
```
NB : si le bloc <dependencies> n'existe pas créer le d'abord

## Créer un controlleur
Sur la classe pricipale ajouter la configuration automatique et la nature RestController :
```java
@RestController
@SpringBootApplication
```
NB : la classe principale devrait se trouver ou être créée sous le package com.formation.microservices

### Créer une méthode controlleur
Créer une méthode accueil qui répond à un mapping "/" par un simple message.
Utiliser l'annotation @RequestMapping("/"))
Vérifier le retour sur http://localhost:8080/

### Lancer l'application 
A partir du main en utilisant la méthode SpringApplication.run():
>  SpringApplication.run(App.class, args);

### Créer une méthode controlleur REST
Créer une méthode qui répond à un mapping "restcustomers" et qui retourne un JSON à partir d'une liste statique de clients et relancer l'application.
NB : utiliser les classes du dossier snippets

Vérifier le retour sur http://localhost:8080/restcustomers

### Ajouter la dependance jackson dataform xml
```xml
	<dependency>
	    <groupId>com.fasterxml.jackson.dataformat</groupId>
	    <artifactId>jackson-dataformat-xml</artifactId>
	</dependency>
```
relancer en spéciafiant le header accept : application/xml
## Ajouter le support JPA
### Ajouter les dépendances necessaires
Sur le pom :
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
### Annoter les classes "domain" pour représenter des entités JPA
Placer les annotations suivantes :
```java
@Entity //sur les classes

@Id
@GeneratedValue(strategy = GenerationType.AUTO) //sur les id (clés primaires)

@OneToMany(mappedBy = "customer", fetch=FetchType.EAGER) //sur la relation un à plusieurs Customer->sales

@JsonIgnore
@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="customer_id") // sur la relation plusieurs à un Sale->Customer

```

### Configurer les parametres :
Dans le fichiers application.yml :
```yaml
server:
  port: XXX
spring:
  jpa:
    show-sql: YYY
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: create-drop
  datasource:
    url: UUUU
    username: sa
    password: PPPP
    driverClassName: CCCC
```
* Pour le port choisir un port qui ne pose pas conflit 9090
* Pour show-sql mettre à true pour pouvoir suivre les requêtes générées
* Pour l'Url choisir la BD H2 en mémoire : jdbc:h2:mem:formation
* Pour le driver choisir : org.h2.Driver
* Pour le password le choix est libre

Ajouter la dépendance vers H2DB pour le tests
```xml
	<dependency>
		<groupId>com.h2database</groupId>
		<artifactId>h2</artifactId>
	</dependency>
```

### Préparer le code 
1. Ajouter l'interface du repository CustomerRepository
Les classes Entity et Repository doivent être dans le même package ou sous package de la classe principale pour être scannés et pris en considération. Alternativement il est possible de specifier un autre package sur les annotations :
```java
@EntityScan("com.formation.spring.domain")
@EnableJpaRepositories("com.formation.spring.repository")
```
2. Ajouter au reposotory une méthode de recherche par nom ou prénom
```java
	@Query("Select c from Customer c where c.firstName like %:namepart% or c.lastName like %:namepart%")
    List<Customer> findByNameContaining(@Param(value="namepart") String namepart);
```
3. Modifier la méthode REST du controlleur qui retourne la liste des utilisateurs afin d'utiliser la BD.


## Activer SwaggerUI sur l'application (down top)
### Ajouter les dépendance :
```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.8.0</version>
</dependency>
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.8.0</version>
</dependency>
```

### Ajouter le support de swagger à la classe de configuration
```java
@EnableSwagger2
```

### Activer Swagger sur l'application en indiquant le bon package à scanner:
```java
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.abc.xyz"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo());
    }
```
### Introduire la première documentation du WS concernant l'application et l'auteur :

```java
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("App Title")
            .description("Customer Management REST API")
            .contact(new Contact("XNOM YPRENON", "www.xyz.net", "x@y.net"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();

    }
```
### Documenter un controlleur :
```java
	@ApiOperation(value = "View a list of available customers", response = List.class)
	@ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successfully retrieved list"),
	@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
```
### Verifier le front et le end point swagger
Génération de la définition de l'API
* http://localhost:9090/v2/api-docs
Génération de l'interface utilisateur pour explorer et tester l'API
http://localhost:9090/swagger-ui.html

### Ajouter des messages personnalisés pour des codes retours donnés
.useDefaultResponseMessages(false)                                   
.globalResponseMessage(RequestMethod.GET,                     
  newArrayList(new ResponseMessageBuilder()   
    .code(500)
    .message("500 message")
    .responseModel(new ModelRef("Error"))
    .build(),
    new ResponseMessageBuilder() 
      .code(403)
      .message("Forbidden!")
      .build()));

## Activer le module actuator :
### Ajouter la dependance Actuator :
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Mettre les infos de l'application au format YAML et les copier sur le fichier application.yml 
    info.app.name=Formation Spring 4
    info.app.description=Gestion des clients avec Spring boot
    info.app.version=1.0.0

### Vérifier les endpoints :
* /info
* /health

### Désactiver la securité pour les status et trace (application.yml)

    management.security.enabled=false

### Vérifier les endpoints :
* /metrics
* /trace



# Troubleshooting
1. Problèmes de syntaxe sur eclipse
  * Faire un click droit sur le projet -> maven -> update project pour prendre en condideration les librairies nouvellement ajoutée au pom
  * Vérifier si le projet se compile avec maven malgré les erreurs Eclipse (mauvaise synchronisation)
  * Faire une premier build install avec une classe principale pour forcer le téléchargement des dépendances.
  * Parfoit maven télécharge des dépendances incomplètes (reconnaissables par des problèmes d'ouverture de zips), il faut supprimer à partir de .m2/repository et relancer
2. Problèmes sur le lancement
  * Vérifier qu'il n'existe pas un process qui occupe les même ports
