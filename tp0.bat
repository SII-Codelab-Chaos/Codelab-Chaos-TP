@echo off

echo -----------------------------------------------------------
echo Lancement du script tp0.bat  
echo -----------------------------------------------------------

cd TP1-fusiion-gestion-competences/

call mvn install -DskipTests

echo -----------------------------------------------------------
echo Install maven TP1 terminé : OK
echo -----------------------------------------------------------

cd ..

docker pull fusiion/sii-codelab-chaos-gestion-competences:latest

docker pull fusiion/sii-codelab-chaos-gestion-collaborateurs:latest

docker pull fusiion/sii-codelab-chaos-gestion-clients:latest

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

call mvn install -DskipTests

echo -----------------------------------------------------------
echo Install maven TP2 terminé : OK
echo -----------------------------------------------------------

cd ../TP3-kubernetes-yaml/gatling/

call mvn install -DskipTests

cd ../..

echo -----------------------------------------------------------
echo Install maven TP3 terminé : OK
echo -----------------------------------------------------------
echo Fin du script tp0.bat
echo -----------------------------------------------------------

@echo on
