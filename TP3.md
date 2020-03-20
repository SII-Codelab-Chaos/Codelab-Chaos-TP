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
$ kubectl config set-context --current --namespace=fusiion
$ kubectl apply -k .
NAME                                              READY   STATUS              RESTARTS   AGE
fusiion-authentification-7c94d57644-dfcn4         0/1     Pending             0          1s
fusiion-authentification-7c94d57644-lxjn5         0/1     ContainerCreating   0          1s
fusiion-authentification-7c94d57644-v6cqf         0/1     Pending             0          1s
fusiion-gestion-clients-77f94d9c68-bjnv4          0/1     Pending             0          0s
fusiion-gestion-clients-77f94d9c68-fg9n7          0/1     Pending             0          1s
fusiion-gestion-clients-77f94d9c68-fqvtq          0/1     Pending             0          0s
fusiion-gestion-collaborateurs-6ff4dfb644-7gzdf   0/1     Pending             0          0s
fusiion-gestion-collaborateurs-6ff4dfb644-kkgj8   0/1     Pending             0          0s
fusiion-gestion-collaborateurs-6ff4dfb644-mfrd5   0/1     Pending             0          1s
fusiion-gestion-competences-6b6df9dc4-6mjwg       0/1     Pending             0          0s
```

> 🐵 Il est tout à fait possible de démarrer FuSIIon avec kubectl si vous connaissez cet outil. Néanmoins, ça n'est pas l'objet de ce tp, les scripts run.sh et delete.sh sont la pour vous faire gagner du temps !

## Chaos-Monkey for Springboot

### Redémarrer l'application sur un cluster kubernetes local avec des chaos-monkey activés sur nos différents micro-services

Configurer les variables d'environnement dans les fichiers de déploiement kubernetes pour chaque service : Compétences, Collaborateur, Authentification et Clients

```shell
    codelab-chaos\TP3-kubernetes-yaml\app\deployments-fusiion\authentification-deployment-fusiion.yaml
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
$ kubectl replace -f app/deployments-fusiion/
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

Gatling offre la possibilité de pusher les métriques/données du tir directement dans un graphite afin de mutualiser les résultats et des les visualiser sur Grafana.
Pour ce faire, nous avons mis à votre distribution un graphite afin de partager vos différents résultats, il suffit d'ajouter/modifier la configuration gatling (gatling/src/test/resources/gatling.conf) :

```
gatling {
  ...
  data {
    writers = [console, file, graphite]      # The list of DataWriters to which Gatling write simulation data (currently supported : console, file, graphite, jdbc)
    ...
    graphite {
      light = false              # only send the all* stats
      host = "xx.xx.xx.xx"         # The host where the Carbon server is located
      port = 2003                # The port to which the Carbon server listens to (2003 is default for plaintext, 2004 is default for pickle)
      protocol = "tcp"           # The protocol used to send data to Carbon (currently supported : "tcp", "udp")
      rootPathPrefix = "TP3.gatling.USERNAME" # The common prefix of all metrics sent to Graphite
      bufferSize = 8192          # Internal data buffer size, in bytes
      writePeriod = 10            # Write period, in seconds
    }
  }
}
```

Lancer le tir

```shell
cd gatling
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

Ouvrir le rapport du tir de charge

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP3-kubernetes-yaml\gatling\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## ChaosKube
Votre application du quotidien possède déjà une release et vous ne voulez pas la retoucher pour inclure Chaos-Monkey for Springboot. Pas de panique, il est tout de même très facile de créer le chaos dans votre application déployé par Kubernetes. Durant ce Hands-on, nous allons utiliser ChaosKube. 

ChaosKube est un outil open source de test du chaos. Il tue périodiquement des pods aléatoires (préfixé) dans votre cluster Kubernetes.

#### 1) Suppression de l'activation de ChaosMonkey for Springboot

Supprimez les propriétés ajoutés durant la première phase de ce TP.

#### 2) Sélection des pods à tuer par chaosMonkey
Par défaut, ChaosKube tue tout les pods comme il veut. Afin d'éviter cela et de cadrer un maximum son impact, nous allons ajouter une annotation pour les différents pods de Fusiion.

```
chaos.alpha.kubernetes.io/enabled: "true"
```

Ajouter cette annotation dans les quatre configurations de déploiements de fusiion (authentification-deployment-fusiion.yaml, clients-deployment-fusiion.yaml, collaborateurs-deployment-fusiion.yaml, competences-deployment-fusiion.yaml).

```
apiVersion: apps/v1beta2
kind: Deployment
metadata:
 ...
spec:
  ...
  template:
    metadata:
      annotations:
        chaos.alpha.kubernetes.io/enabled: "true"
```

Relancer l'application avec ces nouvelles annotations.

```shell
$ kubectl replace -f app/deployments-fusiion/
```

#### 3) Exécution de ChaosMonkey
Vous allez devoir configurer le lancement de votre ChaosMonkey afin de lui demander de tuer tout les pods ayant l'annotation précendante, se trouvant dans le namespace fusiion et avec un interval de 5 secondes.
Ajouter les arguments dans le fichier de déploiement de chaoskube (chaoskube/chaoskube.yaml).

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: chaoskube
  labels:
    app: chaoskube
spec:
  template:
    spec:
      serviceAccountName: chaoskube
      containers:
      - name: chaoskube
        ...
        args:
        # only consider pods with this annotation
        - --annotations=chaos.alpha.kubernetes.io/enabled=true
        # exclude all pods in the kube-system namespace
        - --namespaces=fusiion
        # kill a pod every 7 sec
        - --interval=7s
        # terminate pods for real: this disables dry-run mode which is on by default
        - --no-dry-run
        - --url-api=http://192.168.0.11
        - --user-name=USERNAME
        - --enable-push-api
        - --debug
       ...
```

C'est partit pour déclencher le chaos dans votre application :
```
kubectl create -f chaoskube/
```
#### 4) Lancement du tir gatling


```shell
cd gatling
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

Ouvrir le rapport du tir de charge

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP3-kubernetes-yaml\gatling\target\gatling\basicsimulation-numero_de_simulation\index.html
```

A la fin de chaque tir, il est préférable de stopper le chaosKube :

```
cd ..
kubectl delete -f chaoskube/
```

## Bonus

Si vous êtes un peu en avance et que votre pc le permet, lancer FuSIIon avec 2 ou 3 instances de chaque services (ou à minima l'authentification).

## Modifier les yaml Kubernetes

Configurer le nombre de replica de chaque service dans les fichiers de déploiement Kubernetes de chaque service

```shell
    codelab-chaos\TP3-kubernetes-yaml\deployments-fusiion\authentification-deployment-fusiion.yaml
    spec:
      replicas: 2
```

Relancer l'application avec les nouvelles propriétés

```shell
$ kubectl replace -f app/deployments-fusiion/
```

> 🐵  Il peut être intéressant d'identifier les services "critiques", et de leurs allouer plus de ressources. Dans notre cas, le service authentification est un "Single Point of Failure". N'hésitez pas à lui allouer un replica supplémentaire.

Relancer le chaoskube :

```
kubectl create -f chaoskube/
```

Relancer un tir de charge (à la racine du répertoire TP3-kubernetes-yaml/gatling/) :

```shell
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

Ouvrir le nouveau rapport

```shell
Please open the following file: ...\Codelab-Chaos-TP\TP3-kubernetes\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## Conclusion et debriefing

> 🐵 N'oubliez pas de stopper votre application en local à la fin de ce tp

```shell
$ kubectl delete -k .
```
