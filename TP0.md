# TP0

## Prérequis

* JDK 1.8
* Maven
* Docker (pour windows : 'docker for windows')
* Kubernetes

## Récupérer les sources depuis git

```shell
./tp0.sh

```

## Vérification
Trois nouveaux répertoires :

* fusiion-gestion-competences 
* gatling-maven-plugin-demo
* kubernetes-yaml


Tapez la commande suivante : 
```shell
docker images
```
Avoir les 11 images docker suivantes :
* fusiion/sii-codelab-chaos-statistiques
* fusiion/sii-codelab-chaos-notification
* fusiion/sii-codelab-chaos-gestion-competences
* fusiion/sii-codelab-chaos-gestion-collaborateurs
* fusiion/sii-codelab-chaos-gestion-clients
* fusiion/sii-codelab-chaos-gaming
* fusiion/sii-codelab-chaos-authentification
* fusiion/sii-codelab-chaos-front
* rabbitmq
* bitnami/mongo
* neo4j