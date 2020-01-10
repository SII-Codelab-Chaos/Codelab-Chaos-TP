#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script tp0.sh  
echo -----------------------------------------------------------

cd ..

git clone https://github.com/SII-Codelab-Chaos/Gatling-Load-Tests-FuSIIon.git

git clone https://github.com/SII-Codelab-Chaos/kubernetes-yaml.git

git clone https://github.com/SII-Codelab-Chaos/fusiion-gestion-competences.git

docker pull fusiion/sii-codelab-chaos-statistiques

docker pull fusiion/sii-codelab-chaos-notification

docker pull fusiion/sii-codelab-chaos-gestion-competences

docker pull fusiion/sii-codelab-chaos-gestion-collaborateurs

docker pull fusiion/sii-codelab-chaos-gestion-clients

docker pull fusiion/sii-codelab-chaos-gaming

docker pull fusiion/sii-codelab-chaos-authentification

docker pull fusiion/sii-codelab-chaos-front

docker pull bitnami/mongodb:3.6.6

docker pull neo4j:3.1

docker pull rabbitmq:management