# TP2

"On a des doutes sur la tolérance aux pannes de notre application, en fait personne n'a jamais regardé !”
Comment est ce qu'on peut faire un état des lieux de la situation ?"

## Objectifs

* Lancer l'application FuSIIon via docker
* Lancer un tir de charge de reference
* Configurer les propriétés du chaos-monkey en mode latency
* Lancer un second tir de charge sur l'application FuSIIon
* Configurer les propriétés du chaos-monkey en mode killing
* Lancer un troisième tir de charge sur l'application FuSIIon
* Analyser les résultats de ces tirs

## FuSIIon

### Lancer FuSIIon en local

Aller dans le répertoire "TP2-docker-gatling"

```shell
cd TP2-docker-gatling
```

Lancer nos différents applicatifs  et nos bases de données en local.
```shell
docker-compose up -d
```

Vérifier que les bases de données et les micro-services sont bien démarrés :

```shell
docker container ps

CONTAINER ID        IMAGE                                                     COMMAND                  CREATED             STATUS              PORTS                                                                                       NAMES
8336a1521f71        fusiion/sii-codelab-chaos-gestion-clients:latest          "java -XX:+UnlockExp…"   7 seconds ago       Up 6 seconds        8080/tcp, 0.0.0.0:8084->8084/tcp                                                            fusiion-gestion-clients
c001854b9395        fusiion/sii-codelab-chaos-gestion-collaborateurs:latest   "java -XX:+UnlockExp…"   7 seconds ago       Up 6 seconds        8080/tcp, 0.0.0.0:8083->8083/tcp                                                            fusiion-gestion-collaborateurs
47fbba6a4138        fusiion/sii-codelab-chaos-gestion-competences:latest      "java -XX:+UnlockExp…"   7 seconds ago       Up 6 seconds        8080/tcp, 0.0.0.0:8081->8081/tcp                                                            fusiion-gestion-competences
bc90ce18db5b        fusiion/sii-codelab-chaos-authentification:latest         "java -XX:+UnlockExp…"   8 seconds ago       Up 7 seconds        0.0.0.0:8080->8080/tcp                                                                      fusiion-authentification
84770180388c        fusiion/neo4j-docker:latest                               "/docker-entrypoint.…"   10 seconds ago      Up 8 seconds        7473/tcp, 0.0.0.0:17474->7474/tcp, 0.0.0.0:17687->7687/tcp                                  neo4j
e434a52c04b3        fusiion/bitnami-mongodb:latest                            "/app-entrypoint.sh …"   10 seconds ago      Up 8 seconds        0.0.0.0:27017->27017/tcp                                                                    mongodb
6cf87dab1620        fusiion/rabbitmq:latest                                   "docker-entrypoint.s…"   10 seconds ago      Up 8 seconds        4369/tcp, 5671/tcp, 15671/tcp, 25672/tcp, 0.0.0.0:8088->5672/tcp, 0.0.0.0:8087->15672/tcp   rabbitmq
```
 
## Gatling

### Création du tir de charge

#### Configurer le tir de charge dans le fichier : 

Codelab-Chaos-TP/TP2-docker-gatling/src/test/scala/fusiion/BasicSimulation.scala

Pour cela nous allons proceder étape par étape, et nous aider de la documentation de Gatling :

[https://gatling.io/docs/current/general/simulation_structure](https://gatling.io/docs/current/general/simulation_structure)

#### Définition du protocole de communication

```shell
  val httpProtocol = http
    .baseUrl("http://localhost")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
```

#### Définition du scénario fonctionnel

Construire le scénario fonctionnel que nous allons utiliser pour ce tir de charge à l'aide des informations suivante et de la documentaion de Gatling

[https://gatling.io/docs/current/general/scenario](https://gatling.io/docs/current/general/scenario)

Ajouter le début du scénario avec un premier appel au service d'authentification

```shell
  val scn = scenario("BasicSimulation")
    .exec(http("authentication") // Nom de l\'appel dans le rapport gatling
      .post(":8080/login") // appel HTTP POST sur la ressource REST /gestionAuthentification/login
      .body(StringBody("{\"username\" : \"pgaultier\", \"password\" : \"password\"}")) // body du POST avec user/password
      .check(header("Authorization").saveAs("token")) // stockage du token JWT dans une variable token
    )
    .pause(2) // pause de 2 seconde pour simuler un vrai utilisateur
```

Ajouter un deuxième appel au scénario en récuperant la liste des collaborateurs

```shell
    .exec(http("Collaborateur/pgaultier")
        .get(":8083/collaborateurs/pgaultier@sii.fr")
        .header("Authorization", "${token}")
        .check(status.is(session => 200))
    )
    .pause(2)
```

Completer la fin du scénario fonctionnel du tir de charge à l'aide de la feature suivante qui décrit le comportement attendu.

Pour cela, utiliser ce [SWAGGER](/fusiion-swagger/) qui décrit les endpoints REST de FuSIIon.

Adresses des differents services :

| Service              |         Adresse     
| :------------------- | :--------------------: |
| authentication       |     localhost:8080     |
| competences          |     localhost:8081     |
| collaborateurs       |     localhost:8083     |
| clients              |     localhost:8084     |

<br/>

```gherkin
Feature: FuSIIon cas nominal
Scenario: Connection a FuSIIon puis parcours sur l'application

Given Soit un utilisateur de FuSIIon
And un username "pgaultier@sii.fr"
And un password "password"
When l'utilisateur se connecte via la mire d'authentification de FuSIIon avec ses identifiants
Then il récupere un token d'authentification JWT

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande la liste des collaborateurs
Then il récupere une liste de collaborateurs

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande la liste des competences
Then il récupere une liste de competence

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande le profil d'un collaborateur avec son identifiant
Then il récupere le collaborateur correspondant à son identifiant 

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande la liste des clients
Then il récupere une liste de clients
```

#### Définition du set-up du tir

Completer le setup du tir à l'aide des informations suivantes et de la documentation de Gatling:

[https://gatling.io/docs/current/general/simulation_setup](https://gatling.io/docs/current/general/simulation_setup)

```shell
# Pour ce TP2, nous allons faire des tirs de charge de 3 minutes, soit 180 secondes.
# FuSIIon est à destination des collaborateurs de SII Atlantique, soit environ 300 personnes.
# Pour que ce tir soit validant, nous avons besoin que 80% des requetes soit en succès et moins de 5% d'erreur sur le service d'authentification.

  setUp(
    scn.inject(
      rampUsers(nb_User) during (nb_Seconde seconds))).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.gt(percentage_Successful_Resquest),
      details("authentication").failedRequests.percent.lt(percentage_Failed_Request)
    )
```

> 🐵 Si il vous reste du temps à la fin de ce TP, vous pouvez rajouter d'autres assertions, par exemple sur le temps de reponses ou le nombre de requetes par secondes : [https://gatling.io/docs/current/general/assertions](https://gatling.io/docs/current/general/assertions)

### Lancer un tir de charge

Lancer le tir

```shell
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

> 🐵 Vous pouvez suivre l'évolution des résultats du tir de charge en console pendant toute la durée du tir

Ouvrir le rapport du tir de charge 
Ce rapport est disponible via le lien en en console à la fin de l'éxecution du goal maven

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP2-docker-gatling\target\gatling\basicsimulation-numero_de_simulation\index.html
```
 
## Chaos-Monkey for Springboot : LATENCY

### Redémarrer l'application avec des chaos-monkey activés sur nos différents micro-services

Configurer les variables d'environnement dans le fichier docker-compose.yml pour chaque service : Compétences, Collaborateur, Authentification et Clients

```properties
    environment:
        - SPRING_PROFILES_ACTIVE=chaos-monkey
        - CHAOS_MONKEY_ENABLED=true
        - CHAOS_MONKEY_LEVEL=10
        - CHAOS_MONKEY_KILL_APPLICATION_ACTIVE=false
        - CHAOS_MONKEY_LATENCY_ACTIVE=true
        - CHAOS_MONKEY_LATENCY_RANGE_START=800
        - CHAOS_MONKEY_LATENCY_RANGE_END=2000
```

Relancer l'application avec les nouvelles propriétés

```shell
docker-compose down
docker-compose up -d
```

Vérifier que les services ont bien démarrés avec Chaos-monkey dans les logs de démarrage

```shell
    docker container ps

    docker logs idDuContainer

    2020-01-03 14:25:20.895  INFO 1 --- [           main] d.c.s.b.c.monkey.component.ChaosMonkey   : 
         _____ _                       __  __             _
        / ____| |                     |  \/  |           | |
       | |    | |__   __ _  ___  ___  | \  / | ___  _ __ | | _____ _   _
       | |    | '_ \ / _` |/ _ \/ __| | |\/| |/ _ \| '_ \| |/ / _ | | | |
       | |____| | | | (_| | (_) \__ \ | |  | | (_) | | | |   |  __| |_| |
        \_____|_| |_|\__,_|\___/|___/ |_|  |_|\___/|_| |_|_|\_\___|\__, |
                                                                    __/ |
        _ready to do evil!                                         |___/
    
    :: Chaos Monkey for Spring Boot                                    ::
```
  
## Gatling

### Lancer un tir de charge

Lancer le tir

```shell
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

> 🐵 Pendant l'éxecution de ce tir ( 3 minutes), n'hesitez pas à poser des questions à vos speakers !

Ouvrir le rapport du tir de charge 

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP2-docker-gatling\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## Chaos-Monkey for Springboot : KILLING

### Redémarrer l'application avec des chaos-monkey activés sur nos différents micro-services

Configurer les variables d'environnement dans le fichier docker-compose.yml pour chaque service : Compétences, Collaborateur, Authentification et Clients

```properties
    environment:
        - SPRING_PROFILES_ACTIVE=chaos-monkey
        - CHAOS_MONKEY_ENABLED=true
        - CHAOS_MONKEY_LEVEL=250
        - CHAOS_MONKEY_KILL_APPLICATION_ACTIVE=true
        - CHAOS_MONKEY_LATENCY_ACTIVE=true
        - CHAOS_MONKEY_LATENCY_RANGE_START=1000
        - CHAOS_MONKEY_LATENCY_RANGE_END=10000
```

Relancer l'application avec les nouvelles propriétés

```shell
docker-compose down
docker-compose up -d
```

## Gatling

### Lancer un tir de charge

Lancer le tir

```shell
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

> **Note :** Ce tir de charge va durer 3 minutes, un tir de charge peut durer plusieurs heures !

Ouvrir le rapport du tir de charge 

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP2-docker-gatling\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## Conclusion et debriefing

> 🐵 N'oubliez pas de stopper votre application en local à la fin de ce tp

```shell
docker-compose down
```
