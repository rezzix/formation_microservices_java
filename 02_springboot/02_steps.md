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
    <version>1.5.7.RELEASE</version>
</parent>
```
NB: supprimer le groupe et version existants

Ajouter la dépendance boot starter web:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
NB : si le bloc dependencies n'existe pas créer le d'abord

## Créer un controlleur
Sur la classe pricipale ajouter la configuration automatique et la nature RestController :
```java
@Controller
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
Créer une méthode qui répond à un mapping "getsomecustomers" et qui retourne un JSON à partir d'une liste statique de clients et relancer l'application
NB : utiliser les classes du dossier snippets

Vérifier le retour sur http://localhost:8080/restcustomers

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
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
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
Dans le fichiers application.properties :
```bash
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
    spring.datasource.url=jdbc:h2:mem:formation
    spring.datasource.username=sa
    spring.datasource.password=changeme
    spring.datasource.driverClassName=org.h2.Driver
```
### Préparer le code 
1. Configurer le scan automatique des composants, Utiliser l'annotation 
  * @ComponentScan({"com.formation.spring"})
2. Ajouter l'interface du repository CustomerRepository
3. Copier les classes Entity et parametrer leur emplacement dans la classe de lancement
  * @EntityScan("com.formation.spring.domain")
4. Activer le repository ajouté
  * @EnableJpaRepositories("com.formation.spring.repository")
5. Ajouter une méthode REST du controlleur qui retourne la liste des utilisateurs de la BD

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
## Activer Swagger sur l'application en indiquant le bon package à scanner:
```java
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.abc.xyz"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo());
    }
```
## Introduire la première documentation du WS concernant l'auteur :

```java
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
            .description("Customer Management REST API")
            .contact(new Contact("Ossama Boughaba", "www.mederp.net", "boughaba@mederp.net"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();

    }
```
## Documenter un controlleur :
```java
    @ApiOperation(value = "View a list of available cutomers", response = List.class)
    @ApiResponses(value = {
	@ApiResponse(code = 200, message = "Successfully retrieved list"),
	@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
```

## Activer le module actuator :
### Ajouter la dependance Actuator :
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Ajouter les infos de l'application dans le fichier application.properties
    info.app.name=Formation Spring 4
    info.app.description=Gestion des clients avec Sprin boot
    info.app.version=1.0.0

### Vérifier les endpoints :

* /health
* /info

### Desactiver la securité pour les status et trace

    management.security.enabled=false

### Vérifier les endpoints :
* /metrics
* /trace



# Troubleshooting
1. Problèmes de syntaxe sur eclipse
  * Vérifier si le projet se compile avec maven malgré les erreurs Eclipse (mauvaise synchronisation)
  * Faire une premier build install avec une classe principale pour forcer le téléchargement des dépendances.
  * Parfoit maven télécharge des dépendances incomplètes (reconnaissables par des problèmes d'ouverture de zips), il faut supprimer à partir de .m2/repository et relancer
2. Problèmes sur le lancement
  * Vérifier qu'il n'existe pas un process qui occupe les même ports
