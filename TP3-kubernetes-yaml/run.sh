#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script run.sh  
echo -----------------------------------------------------------

kubectl create -f 'fusiion-namespace.yaml' | echo 1/7 - create namespace Done

kubectl create -f 'fusiion-rbac.yaml' | echo 2/7 - create rbac Done

kubectl create -f 'app-fusiion\secrets-config\' | echo 3/7 - create secrets Done

kubectl create -f 'app-fusiion\statefulsets-fusiion\' | echo 4/7 - create statefulsets Done

kubectl create -f 'app-fusiion\default\' | echo 5/7 - create default Done

kubectl create -f 'kubernetes-metrics-server\'  | echo 6/7 - create metrics Done

# pas de param
if [ $# == 1 ] ; then
    if [ $1 == "all" ] ; then
        kubectl create -f 'app-fusiion\all\' | echo 7a/7 - ALL deployments Done
    fi
fi  

kubectl create -f 'app-fusiion\deployments-fusiion\' | echo -e '7/7 - deployments Done \n-----------------------------------------------------------  \n ALL DONE \n-----------------------------------------------------------'

kubectl config set-context --current --namespace=fusiion

kubectl get pods