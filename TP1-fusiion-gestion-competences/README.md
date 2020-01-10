# Gestion-Competence

Micro service de gestion des compétences de l'application FuSIIon

## Réalisé avec

* [Spring boot](https://spring.io/projects/spring-boot) - Framework J2EE ***version 2.0***
* [Maven](https://maven.apache.org/) - Gestion de dépendances ***version xxx***
* [jdk\_8u161](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* architecture en ***microservices*** avec ***conteneurs docker***
* [Neo4j](https://neo4j.com/) ***version 3.1***
* [MongoDB](https://www.mongodb.com/) version docker bitnami/mongodb 3.6.6
* [RabbitMQ](https://www.rabbitmq.com/)

### Prérequis

Pour lancer l'application en local sur votre pc, vous devez avoir un environnement JAVA configuré avec, de préférence le jdk_8u161.
Il faudra préalablement lancer RabbitMq et Neo4J.

### Installation et déploiement

***1.  avec docker***

La commande suivante compilera les sources, exécutera les tests et produira l'image docker du microservice.

```
mvn clean install -Pdocker
```

Il suffit ensuite de lancer un contener avec cette image

```
docker-compose up
```

Le service est accessible à l'adresse localhost:8083
