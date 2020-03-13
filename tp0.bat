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

docker pull fusiion/chaoskube:latest

docker pull fusiion/sii-codelab-chaos-gestion-competences:latest

docker pull fusiion/sii-codelab-chaos-gestion-collaborateurs:latest

docker pull fusiion/sii-codelab-chaos-gestion-clients:latest

docker pull fusiion/sii-codelab-chaos-authentification:latest

docker pull fusiion/rabbitmq:latest

docker pull fusiion/mongo:latest

docker pull fusiion/sii-codelab-chaos-front:latest

docker pull fusiion/neo4j-docker:latest

docker pull fusiion/neo4j-kube:latest

docker pull fusiion/bitnami-mongodb:latest

docker pull fusiion/nginx-ingress-controller:latest

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
