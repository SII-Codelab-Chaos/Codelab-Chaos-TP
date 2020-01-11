#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script delete.sh  
echo -----------------------------------------------------------

kubectl config set-context --current --namespace=default

# pas de param
if [ $# == 1 ] ; then
    if [ $1 == "all" ] ; then
        kubectl delete -f 'app\all\' | echo 1a/6 - delete all deployments
    fi
fi    
kubectl delete -f 'deployments-fusiion\' | echo 1/6 - delete deployments

kubectl delete -f 'app\default\' | echo 2/6 - delete default

kubectl delete -f 'app\statefulsets-fusiion\' | echo 3/6 - delete statefulsets

kubectl delete -f 'app\secrets-config\' | echo 4/6 - delete secrets

kubectl delete -f 'fusiion-rbac.yaml' | echo 5/6 - delete rbac 

kubectl delete -f 'fusiion-namespace.yaml' | echo -e '6/6 - delete namespace \n-----------------------------------------------------------  \nALL DELETE \n-----------------------------------------------------------'