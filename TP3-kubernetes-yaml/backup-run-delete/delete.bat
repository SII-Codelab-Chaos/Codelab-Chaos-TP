@echo off

echo -----------------------------------------------------------
echo Lancement du script delete.sh  
echo -----------------------------------------------------------

kubectl config set-context --current --namespace=default

kubectl delete -f ../app/deployments-fusiion/ | echo 1/6 - delete deployments

kubectl delete -f ../app/default/ | echo 2/6 - delete default

kubectl delete -f ../app/statefulsets-fusiion/ | echo 3/6 - delete statefulsets

kubectl delete -f ../app/secrets-config/ | echo 4/6 - delete secrets

kubectl delete -f ../app/fusiion-rbac.yaml | echo 5/6 - delete rbac 

kubectl delete -f ../app/fusiion-namespace.yaml | echo 6/6 - delete namespace

echo -----------------------------------------------------------
echo Fin du script delete.bat
echo -----------------------------------------------------------

@echo on