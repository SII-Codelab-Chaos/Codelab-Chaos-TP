# TP0

## Prérequis

* JDK 1.8
* Maven
* Docker (pour windows : 'docker for windows')
* Kubernetes

> Pour toute la durée du CodeLab, placez-vous dans un dossier de travail (idéalement à l'abri de votre antivirus). Idéalement, le repository Maven est également à l'abri de votre antivirus

## Récupérer les sources des TP

```shell
git clone https://github.com/SII-Codelab-Chaos/Codelab-Chaos-TP.git
```

## Récupérer les images docker (utiles à partir du TP2) et les dépendances Maven des TP 1 et 2

Sur Linux ou émulateur type Git Bash :

```shell
./tp0.sh
```

Sur Windows :

```shell
./tp0.bat
```

## Vérifications

Taper la commande suivante :

```shell
docker images
```

Vérifier la présence des 14 images docker suivantes :

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