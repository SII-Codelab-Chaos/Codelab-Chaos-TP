@echo off

echo -----------------------------------------------------------
echo Lancement du script run.bat
echo -----------------------------------------------------------

kubectl create -f 'fusiion-namespace.yaml' | echo 1/6 - create namespace Done

kubectl create -f 'fusiion-rbac.yaml' | echo 2/6 - create rbac Done

kubectl create -f 'app\secrets-config\' | echo 3/6 - create secrets Done

kubectl create -f 'app\statefulsets-fusiion\' | echo 4/6 - create statefulsets Done
    
kubectl create -f 'app\default\' | echo 5/6 - create default Done

kubectl create -f 'deployments-fusiion\' | echo -e '6/6 - deployments Done \n-----------------------------------------------------------  \n ALL DONE \n-----------------------------------------------------------'

kubectl config set-context --current --namespace=fusiion

kubectl get 

echo -----------------------------------------------------------
echo Fin du script run.bat
echo -----------------------------------------------------------

@echo on