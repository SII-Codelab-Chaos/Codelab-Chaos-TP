@echo off

echo -----------------------------------------------------------
echo Lancement du script run.bat
echo -----------------------------------------------------------

kubectl create -f ../app/fusiion-namespace.yaml --validate=false | echo 1/6 - create namespace Done

kubectl create -f ../app/fusiion-rbac.yaml --validate=false | echo 2/6 - create rbac Done

kubectl create -f ../app/secrets-config/ --validate=false | echo 3/6 - create secrets Done

kubectl create -f ../app/statefulsets-fusiion/ --validate=false | echo 4/6 - create statefulsets Done
    
kubectl create -f ../app/default/ --validate=false | echo 5/6 - create default Done

kubectl create -f ../app/deployments-fusiion/ --validate=false | echo 6/6 - deployments Done

kubectl config set-context --current --namespace=fusiion

kubectl get pods

echo -----------------------------------------------------------
echo Fin du script run.bat
echo -----------------------------------------------------------

@echo on