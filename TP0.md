# TP0

## Prérequis

* Git
* JDK 1.8
* Maven
* Docker (pour windows : 'docker for windows')
* Kubernetes

> Pour toute la durée du CodeLab, placez-vous dans un dossier de travail (idéalement à l'abri de votre antivirus). Idéalement, le repository Maven est également à l'abri de votre antivirus


## Récupérer les sources des TP

```shell
git clone https://github.com/SII-Codelab-Chaos/Codelab-Chaos-TP.git
```

## Récupérer les images docker (utiles uniquement à partir du TP2) et les dépendances Maven des TP 1 et 2

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

Vérifier la présence des 12 images docker suivantes :

* fusiion/chaoskube:latest
* fusiion/sii-codelab-chaos-gestion-competences:latest
* fusiion/sii-codelab-chaos-gestion-collaborateurs:latest
* fusiion/sii-codelab-chaos-gestion-clients:latest
* fusiion/sii-codelab-chaos-authentification:latest
* fusiion/rabbitmq:latest
* fusiion/mongo:latest
* fusiion/sii-codelab-chaos-front:latest
* fusiion/neo4j-docker:latest
* fusiion/neo4j-kube:latest
* fusiion/bitnami-mongodb:latest
* fusiion/nginx-ingress-controller:latest
