#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script tp0.sh  
echo -----------------------------------------------------------

docker pull fusiion/sii-codelab-chaos-statistiques:latest

docker pull fusiion/sii-codelab-chaos-notification:latest

docker pull fusiion/sii-codelab-chaos-gestion-competences:latest

docker pull fusiion/sii-codelab-chaos-gestion-collaborateurs:latest

docker pull fusiion/sii-codelab-chaos-gestion-clients:latest

docker pull fusiion/sii-codelab-chaos-gaming:latest

docker pull fusiion/sii-codelab-chaos-authentification:latest

docker pull fusiion/sii-codelab-chaos-front:latest

docker pull bitnami/mongodb:3.6.6

docker pull mongo:3.4

docker pull neo4j:3.1

docker pull neo4j:3.1.0

docker pull rabbitmq:management

docker pull quay.io/kubernetes-ingress-controller/nginx-ingress-controller:0.9.0

echo -----------------------------------------------------------
echo Fin du script tp0.sh
echo -----------------------------------------------------------
