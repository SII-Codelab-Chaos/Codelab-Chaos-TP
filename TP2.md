# TP2

"On a des doutes sur la tolérance aux pannes de notre application, en fait personne n'a jamais regardé !”
Comment est ce qu'on peut faire un état des lieux de la situation ?"

## Objectifs

* Lancer l'application FuSIIon via docker
* Configurer les propriétés du chaos-monkey dans le docker compose
* Lancer un tir de charge sur l'application FuSIIon
* Analyser les résultats de ce tir


## FuSIIon

### Lancer FuSIIon en local

Aller dans le répertoire "TP2-docker-gatling"

```shell
cd TP2-docker-gatling
```

Lancer nos différents applicatifs  et nos bases de données en local.
```shell
docker-compose up -d
```

Vérifier que les bases de données et les micro-services sont bien démarrés:

```shell
docker container ps

    CONTAINER ID        IMAGE                                     COMMAND                  CREATED             STATUS              PORTS                                                                                       NAMES
    217a92ed643b        fusiion/fusiion-gestion-competences       "java -jar /opt/fusi…"   20 minutes ago      Up 20 minutes       8080/tcp, 0.0.0.0:8081->8081/tcp                                                            fusiion-gestion-competences
    fc01f10e5a8e        fusiion/fusiion-gestion-clients           "java -jar /opt/fusi…"   20 minutes ago      Up 20 minutes       8080/tcp, 0.0.0.0:8084->8084/tcp                                                            fusiion-gestion-clients
    536f1df87d62        fusiion/fusiion-notification              "java -jar /opt/fusi…"   20 minutes ago      Up 20 minutes       8080/tcp, 0.0.0.0:8090->8090/tcp                                                            fusiion-notification
    2da72da3f284        fusiion/fusiion-statistiques              "java -jar /opt/fusi…"   20 minutes ago      Up 20 minutes       8080/tcp, 0.0.0.0:8082->8082/tcp                                                            fusiion-statistiques
    3dd4a601763f        fusiion/fusiion-gaming                    "java -jar /opt/fusi…"   20 minutes ago      Up 20 minutes       8080/tcp, 0.0.0.0:8086->8086/tcp                                                            fusiion-gaming
    67fb696c7427        fusiion/fusiion-gestion-collaborateurs    "java -jar /opt/fusi…"   20 minutes ago      Up 20 minutes       8080/tcp, 0.0.0.0:8083->8083/tcp                                                            fusiion-gestion-collaborateurs
    f561a33e9028        fusiion/fusiion-authentification:latest   "java -XX:+UnlockExp…"   20 minutes ago      Up 20 minutes       0.0.0.0:8080->8080/tcp                                                                      fusiion-authentification
    6a08656082ff        rabbitmq:management                       "docker-entrypoint.s…"   3 hours ago         Up 36 minutes       4369/tcp, 5671/tcp, 15671/tcp, 25672/tcp, 0.0.0.0:8088->5672/tcp, 0.0.0.0:8087->15672/tcp   rabbitmq
    c6e0577926c7        bitnami/mongodb:3.6.6                     "/app-entrypoint.sh …"   3 hours ago         Up 36 minutes       0.0.0.0:27017->27017/tcp                                                                    mongodb
    dcd1055b23da        neo4j:3.1                                 "/sbin/tini -g -- /d…"   3 hours ago         Up 36 minutes       7473/tcp, 0.0.0.0:17474->7474/tcp, 0.0.0.0:17687->7687/tcp                                  neo4j    
 ```

## Chaos-Monkey for Springboot
 
### Redémarrer l'application avec des chaos-monkey activés sur nos différents micro-services 
 
Configurer les variables d'environnement dans le fichier docker-compose.yml pour chaque service : Compétences, Collaborateur, Authentification et Clients

```properties
    environment:
        - SPRING_PROFILES_ACTIVE=chaos-monkey
        - CHAOS_MONKEY_ENABLED=true
        - CHAOS_MONKEY_LEVEL=250
        - CHAOS_MONKEY_KILL_APPLICATION_ACTIVE=true
```

Relancer l'application avec les nouvelles propriétés
```shell
docker-compose down
docker-compose up -d
```

Vérifier que les services ont bien démarrés avec Chaos-monkey dans les logs de démarrage

```shell
    docker container ps

    docker logs idDuContainer

    2020-01-03 14:25:20.895  INFO 1 --- [           main] d.c.s.b.c.monkey.component.ChaosMonkey   : 
         _____ _                       __  __             _
        / ____| |                     |  \/  |           | |
       | |    | |__   __ _  ___  ___  | \  / | ___  _ __ | | _____ _   _
       | |    | '_ \ / _` |/ _ \/ __| | |\/| |/ _ \| '_ \| |/ / _ | | | |
       | |____| | | | (_| | (_) \__ \ | |  | | (_) | | | |   |  __| |_| |
        \_____|_| |_|\__,_|\___/|___/ |_|  |_|\___/|_| |_|_|\_\___|\__, |
                                                                    __/ |
        _ready to do evil!                                         |___/
    
    :: Chaos Monkey for Spring Boot                                    ::
```
  
## Gatling

### Lancer un tir de charge

Lancer le tir

```shell
mvn -Dgatling.compilerJvmArgs="-Xmx256m" gatling:test
```

> **Note :** Ce tir de charge va durer 4 minutes, un tir de charge peut durer plusieurs heures !

Ouvrir le rapport du tir de charge 

```shell
Please open the following file: ..\Codelab-Chaos-TP\TP2-docker-gatling\target\gatling\basicsimulation-numero_de_simulation\index.html
```

## Conclusion et debriefing

> 🐵 N'oubliez pas de stopper votre application en local à la fin de ce tp

```shell
docker-compose down
```
