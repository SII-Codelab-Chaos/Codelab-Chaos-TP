# TP2

“Le constat est fait : ok, notre applicatif répond.. Mais est vraiment très sensibles aux pannes!” Comment rendre notre application vraiment résiliente ? Comment palier à ce genre de problèmes ?”

## Objectifs

Lancer l'application FuSIIon sur un cluster Kubernetes local
Configurer les properties du chaos-monkey dans les yaml
Lancer un tir de charge sur l'application FuSIIon
Analyser les résultats de ce tir

## FuSIIon

Pour réaliser les étapes suivantes, vous avez besoin d'un cluster kubernetes sur votre poste local ( Docker4Windows ou minikube par exemple)

Comme pour le TP précèdent, nous allons devoir relancer FuSIIOn en local.
Pour cela, on va commencer par arrêter l'application que nous avions lancé avec Docker

    docker-compose down
    
Ensuite, nous allons nous intéresser aux fichiers yaml présents dans le repository https://github.com/SII-Codelab-Chaos/kubernetes-yaml
Ces fichiers contiennent tous ce qu'il faut pour démarrer FuSIIon sur un cluster kubernetes en local.
Le but de ce TP n'étant pas de maitriser Kubernetes, mais d'apporter une solution de résilience, nous avons fait deux scripts: run.sh et delete.sh.
Ces scripts "masquent" la complexité de kubernetes, et permettent de démarrer et d'éteindre le cluster sans connaitre kubectl.
N'hésitez pas à faire sans si vous connaissez un peu cet outil, mais nous ne passerons pas plus de temps que ça dessus.

Nous allons donc lancer FuSIIon sur le cluster local à l'aide de la commande suivante :

    ./run.sh
 
    $ ./run.sh
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


Victoire, nous avons déployé notre applicatif !
 
 ## Chaos-Monkey for Springboot
 
Ensuite, comme précédemment, nous allons configurer nos services pour lancer des assaults avec chaos-monkey.
Ce sont les mêmes images Docker que dans le TP2, le fonctionnement est donc similaire.
Nous allons donc rajouter des variables d'environnement à nos services.
Pour cela, nous allons éditer les fichiers Yaml "deployment" :

    codelab-chaos\kubernetes-yaml\app-fusiion\deployments-fusiion\authentification-deployment-fusiion.yaml
        env:
          - name: SPRING_PROFILES_ACTIVE
            value: chaos-monkey
          - name: CHAOS_MONKEY_ENABLED
            value: "true"
          - name: CHAOS_MONKEY_LEVEL
            value: "250"
          
Nous avons donc nos chaos-monkey actifs sur nos services.
Il nous suffit donc de redémarrer nos applicatifs:

    ./delete.sh
    ./run.sh
        
## Gatling

Nous allons relancer le même tir que précédemment, afin de pouvoir comparer les résultats.
Pour cela, la commande est toujours :

    mvn gatling: test
    
Nous allons également suivre l'évolution de nos containers, pour cela vous pouvez utiliser "kubebox.exe" sous windows, ou la commande watch sou linux/mac.
Nous en reparlerons lors du débriefing, mais intéressez-vous aux redémarrages des pods.
    
Même chose que précédemment, une fois le test terminé vous n'avez plus qu'a suivre le lien et ouvrir le rapport.

## Conclusion et debriefing

Bon et bien ça a l'air de tenir la charge ce coup-ci !
Rendez vous avec vos Speaker et les autres participants pour débriefer.

