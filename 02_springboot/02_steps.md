# Sur Eclipse
## Nouveau projet maven :

Nouveau projet maven avec archetype quickstart ou sans archetype
utiliser le package com.formation.microservices
utiliser le nom d'artifact 02_springboot

## Modifier le project object model (pom.xml)
Modifier la définition du POM en ajoutant:
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.7.RELEASE</version>
    </parent>
```
NB: supprimer le groupe et version existants

Ajouter la dépendance boot web:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
## Créer un controlleur
Sur la classe pricipale ajouter la configuration automatique et la nature RestController :
* @RestController
* @EnableAutoConfiguration

### Créer une méthode controlleur
Créer une méthode qui répond à un mapping "/accueil" par un simple message

### Créer une méthode controlleur REST
Créer une méthode qui répond à un mapping "restcustomers" et qui retourne un JSON à partir d'une liste de clients

### Lancer l'application 
A partir du main en utilisant SpringApplication:
>  SpringApplication.run(App.class, args);

## Ajouter le support JPA
### Ajouter les dépendances necessaires
Sur le pom :

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
		
### lancer la BD et vérifier qu'elle est accessible
lancer la commande :

    >java -jar /home/user/.m2/repository/com/h2database/h2/V.R.R/h2-V.R.R.jar -tcpAllowOthers
    
V.R.R = version, release, revision
		
### Configurer les parametres :
Dans le fichiers application.properties :

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties..hibernate.dialect=org.hibernate.dialect.H2Dialect
    spring.datasource.url=jdbc:h2:tcp://localhost:9092/~/tmp/sales
    spring.datasource.username=sa
    spring.datasource.password=changeme
    spring.datasource.driverClassName=org.h2.Driver

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

## Activer Swagger sur l'application en indiquant le bon package à scanner:

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.abc.xyz"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo());
    }

## Introduire la première documentation du WS concernant l'auteur :


    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
            .description("Customer Management REST API")
            .contact(new Contact("Ossama Boughaba", "www.mederp.net", "boughaba@mederp.net"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();

    }

## Documenter un controlleur :

	    @ApiOperation(value = "View a list of available cutomers", response = List.class)
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	    })


## Activer le module actuator :
### Ajouter la dependance Actuator :
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>


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
2. Problèmes sur le lancement
  * Vérifier qu'il n'existe pas un process qui occupe les même ports
