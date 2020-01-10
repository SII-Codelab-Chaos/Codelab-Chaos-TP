#!/bin/bash

echo -----------------------------------------------------------
echo Lancement du script delete.sh  
echo -----------------------------------------------------------

kubectl config set-context --current --namespace=default

# pas de param
if [ $# == 1 ] ; then
    if [ $1 == "all" ] ; then
        kubectl delete -f 'app-fusiion\all\' | echo 1a/7 - delete all deployments
    fi
fi    
kubectl delete -f 'app-fusiion\deployments-fusiion\' | echo 1/7 - delete deployments

kubectl delete -f 'app-fusiion\default\' | echo 2/7 - delete default

kubectl delete -f 'app-fusiion\statefulsets-fusiion\' | echo 3/7 - delete statefulsets

kubectl delete -f 'app-fusiion\secrets-config\' | echo 4/7 - delete secrets

kubectl delete -f 'fusiion-rbac.yaml' | echo 5/7 - delete rbac 

kubectl delete -f 'kubernetes-metrics-server\'  | echo 6/7 - delete metrics

kubectl delete -f 'fusiion-namespace.yaml' | echo -e '7/7 - delete namespace \n-----------------------------------------------------------  \nALL DELETE \n-----------------------------------------------------------'