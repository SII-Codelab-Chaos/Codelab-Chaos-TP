# TP0

## Prérequis

* JDK 1.8
* Maven
* Docker (pour windows : 'docker for windows')
* Kubernetes

## Récupérer les images docker (utiles à partir du TP2)

```shell
./tp0.sh
```

## Vérifications

Taper la commande suivante : 
```shell
docker images
```

Avoir les 14 images docker suivantes :
* fusiion/sii-codelab-chaos-statistiques:latest
* fusiion/sii-codelab-chaos-notification:latest
* fusiion/sii-codelab-chaos-gestion-competences:latest
* fusiion/sii-codelab-chaos-gestion-collaborateurs:latest
* fusiion/sii-codelab-chaos-gestion-clients:latest
* fusiion/sii-codelab-chaos-gaming:latest
* fusiion/sii-codelab-chaos-authentification:latest
* fusiion/sii-codelab-chaos-front:latest
* bitnami/mongodb:3.6.6
* mongo:3.4
* neo4j:3.1
* neo4j:3.1.0
* rabbitmq:management
* quay.io/kubernetes-ingress-controller/nginx-ingress-controller:0.9.0