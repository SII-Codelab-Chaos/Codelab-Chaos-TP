# TP3

“Le constat est fait : ok, notre applicatif répond.. Mais est vraiment très sensibles aux pannes!” Comment rendre notre application vraiment résiliente ? Comment palier à ce genre de problèmes ?”

## Objectifs

* Lancer l'application FuSIIon sur un cluster Kubernetes local
* Configurer les properties du chaos-monkey dans les yaml
* Lancer un tir de charge sur l'application FuSIIon
* Analyser les résultats de ce tir

Pour réaliser les étapes suivantes, vous avez besoin d'un cluster kubernetes sur votre poste local ( Docker4Windows ou minikube par exemple)


## FuSIIon

### Lancer FuSIIon en local

Aller dans le répertoire "TP3-kubernetes-yaml"

```shell
cd TP3-kubernetes-yaml
```

Lancer nos différents applicatifs  et nos bases de données en local.

```shell
./run.sh

    -----------------------------------------------------------
    Lancement du script run.sh
    -----------------------------------------------------------
    1/7 - create namespace Done
    2/7 - create rbac Done
    3/7 - create secrets Done
    4/7 - create statefulsets Done
    5/7 - create default Done
    6/7 - create metrics Done
    7/7 - deployments Done
    -----------------------------------------------------------
     ALL DONE
    -----------------------------------------------------------
    Context "docker-desktop" modified.
    NAME                                              READY   STATUS              RESTARTS   AGE
    fusiion-authentification-7d47f4f5b6-m2rzh         0/1     ContainerCreating   0          0s
    fusiion-gestion-clients-6d4c888fbb-xrqwp          0/1     Pending             0          0s
    fusiion-gestion-collaborateurs-686df4d58b-2tkxj   0/1     Pending             0          0s
    fusiion-gestion-competences-fc5c5848d-kj5nq       0/1     Pending             0          0s
    mongo-0                                           0/1     ContainerCreating   0          1s
    neo4j-core-0                                      0/1     ContainerCreating   0          1s
    nginx-ingress-574f74c78-hkh5v                     0/1     ContainerCreating   0          1s
    rabbitmq-0                                        0/1     ContainerCreating   0          1s
```

> Note : Il est tout à fait possible de demarer FuSIIon avec kubectl si vous connaissez cet outil. Néanmoins, ça n'est pas l'objet de ce tp, les scripts run.sh et delete.sh sont la pour vous faire gagner du temps !

 
 ## Chaos-Monkey for Springboot
 
 ### Redémarrer l'application sur un cluster kubernetes local avec des chaos-monkey activés sur nos différents micro-services 

Configurer les variables d'environnement dans les fichiers de déploiement kubernetes pour chaque service : Compétences, Collaborateur, Authentification et Clients

```shell
    codelab-chaos\kubernetes-yaml\app-fusiion\deployments-fusiion\authentification-deployment-fusiion.yaml
        env:
          - name: SPRING_PROFILES_ACTIVE
            value: chaos-monkey
          - name: CHAOS_MONKEY_ENABLED
            value: "true"
          - name: CHAOS_MONKEY_LEVEL
            value: "250"
          - name: CHAOS_MONKEY_KILL_APPLICATION_ACTIVE
            value: "true"          
```          

Relancer l'application avec les nouvelles propriétés

```shell
    ./delete.sh
    ./run.sh
```
## Kubebox

Nous vous conseillons fortement d'utiliser l'outil kubebox fourni directement dans ce répertoire. Celui-ci permet de visualiser et de superviser l'état des différents pods.

Lancer kubebok (attention : celui-ci ne fonctionne pas sous git Bash, préférez PowerShell ou Cmder).
Exemple sous windows :
```shell
    ./kubebox-windows.exe
```

## Gatling

### Lancer un tir de charge

Lancer le tir

```shell
cd gatling
mvn gatling:test
```

Ouvrir le rapport du tir de charge 

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP3-kubernetes\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## Bonus

Si vous êtes un peu en avance et que votre pc le permet, lancer FuSIIon avec 2 ou 3 instances de chaque services (ou à minima l'authentification).

## Modifier les yaml Kubernetes

Configurer le nombre de replica de chaque service dans les fichiers de déploiement Kubernetes de chaque service
```shell
    codelab-chaos\kubernetes-yaml\app-fusiion\deployments-fusiion\authentification-deployment-fusiion.yaml
    spec:
      replicas: 3
```       

> Note : Il peut être interessant d'identifier les services "critiques", et de leurs allouer plus de ressources. Dans notre cas, le service authentification est un "Single Point of Failure". N'hésitez pas à lui allouer un replica supplémentaire.

Relancer un tir de charge (à la racine du répertoire TP3-kubernetes-yaml/gatling/) :

```shell
mvn gatling:test
```

Ouvrir le nouveau rapport 

```shell
Please open the following file: ...\Codelab-Chaos-TP\TP3-kubernetes\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## Conclusion et debriefing

> Note : N'oubliez pas de stopper votre application en local à la fin de ce tp

```shell
./delete.sh
```
