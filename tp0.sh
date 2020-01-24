#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script tp0.sh  
echo -----------------------------------------------------------

cd TP1-fusiion-gestion-competences/

mvn install -DskipTests

echo -----------------------------------------------------------
echo Install maven TP1 terminé : OK
echo -----------------------------------------------------------

cd ..

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
echo Toutes les images docker ont été récupérées : OK
echo -----------------------------------------------------------

cd TP2-docker-gatling/

mvn install -DskipTests

echo -----------------------------------------------------------
echo Install maven TP2 terminé : OK
echo -----------------------------------------------------------

cd ../TP3-kubernetes-yaml/gatling

mvn install -DskipTests

cd ../..

echo -----------------------------------------------------------
echo Install maven TP3 terminé : OK
echo -----------------------------------------------------------
echo Fin du script tp0.sh
echo -----------------------------------------------------------
