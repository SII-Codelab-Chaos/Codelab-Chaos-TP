#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script run.sh  
echo -----------------------------------------------------------

kubectl create -f 'fusiion-namespace.yaml' --validate=false | echo 1/6 - create namespace Done

kubectl create -f 'fusiion-rbac.yaml' --validate=false | echo 2/6 - create rbac Done

kubectl create -f 'app\secrets-config\' --validate=false | echo 3/6 - create secrets Done

kubectl create -f 'app\statefulsets-fusiion\' --validate=false | echo 4/6 - create statefulsets Done
    
kubectl create -f 'app\default\' --validate=false | echo 5/6 - create default Done

# pas de param
if [ $# == 1 ] ; then
    if [ $1 == "all" ] ; then
        kubectl create -f 'app\all\' --validate=false | echo 6a/6 - ALL deployments Done
    fi
fi  

kubectl create -f 'deployments-fusiion\' --validate=false | echo -e '6/6 - deployments Done \n-----------------------------------------------------------  \n ALL DONE \n-----------------------------------------------------------'

kubectl config set-context --current --namespace=fusiion

kubectl get pods