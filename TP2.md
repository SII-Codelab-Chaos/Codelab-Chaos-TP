# TP2

"On a des doutes sur la tolérance aux pannes de notre application, en fait personne n'a jamais regardé !”
Comment est ce qu'on peut faire un état des lieux de la situation ?"

## Objectifs

Lancer l'application FuSIIon via docker
Configurer les properties du chaos-monkey dans le docker compose
Lancer un tir de charge sur l'application FuSIIon
Analyser les résultats de ce tir


## FuSIIon

FuSIIon est une application de gestion des compétences qui a été développé en interne chez SII.
Elle est composée de plusieurs micro-services écrits en Java et d'une application frontend en Angular
Dans ce TP, nous allons surtout nous intéresser à la partie backend de cette application.
Ces micro-services sont conteneurisés, ce qui va nous faciliter la tâche ici.
Pour cela, nous vous avons fournis les images docker de ces micro services.
Commençons par lancer nos différents applicatifs en local.

### Basculement vers la branche GIT "TP2"

```shell
git checkout TP2
```

### Aller dans le nouveau répertoire "TP2-gatling-maven-plugin-demo"

```shell
cd TP2-gatling-maven-plugin-demo
```

Commençons par lancer nos différents applicatifs en local.

    docker-compose up -d
    
Cette commande va lancer les bases de données nécessaires à FuSIIon, ainsi que tous ses micro-services
Pour vérifier cela :

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
 
 Voilà pour le lancement de FuSIIon en local !
 
 ## Chaos-Monkey for Springboot
 
 Entrons dans le vif du sujet :
 Les images que nous vous avons fournis contiennent déjà tout ce qu'il faut pour faire fonctionner le chaos-monkey.
 Nous avons simplement rajouter une dépendance dans le pom.xml :
   
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>chaos-monkey-spring-boot</artifactId>
        <version>2.0.0</version>
    </dependency>
    
Pour tout le reste, il ne s'agit que de configuration !
Nous allons utiliser le docker-compose que nous venons d'utiliser pour passer cette configuration aux différents services.    
Pour cela, nous allons utiliser la section "environnement" des différents services, qui correspond au variables d'environnement de nos micro-services.
Tout d'abord, nous allons spécifier un profil spring "Chaos-monkey" :

    SPRING_PROFILES_ACTIVE=chaos-monkey

Ensuite, nous allons activer chaos-monkey sur ce composant :
    
    CHAOS_MONKEY_ENABLED=true
    
Et voilà, rien de plus compliqué !

Nous avons par default défini un "assault" sur nos services, toujours par des propriétés :
chaos:
  monkey:
    enabled: true
    assaults:
      level: ${CHAOS_MONKEY_LEVEL}
      killApplicationActive: true
      
Cet assault nous dis que pour chaque appel au micro-service, il y aura une certaine probabilité de déclencher un évènement malencontreux, à savoir ici la mort du service en question!
Il existe d'autres assault qui rajoute de la latence ou déclenche des exceptions, nous en reparlerons à la fin de ce Hands-On.
La variable CHAOS_MONKEY_LEVEL nous indique la probabilité de déclencher un assault, il nous faut donc la renseigner également.
Nous allons commencer par mettre 250, ce qui donnera 1 chance sur 250 que notre application s'arrête.

    CHAOS_MONKEY_LEVEL=250
    
Nous sommes donc prêt à relancer notre application pour tout casser ! ( On sait bien que vous êtes venus à ce Hands-On pour ça ;) )

    docker-compose up -d

Et si nous consultons les logs de nos containers

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
    
## Gatling

Nos services sont donc prêt, mais comment allons-nous faire pour tester ?
On pourrai attaquer nos API Rest à la main mais nous allons essayer d'être un peu plus précis, et pour cela, il va falloir s'outiller !
Gatling est un outil open source qui permet de réaliser des tirs de charge, et va ici nous servir à mesurer un peu plus finement le comportement de FuSIIon.
Par chance, vos Speaker vont ont fourni un repository déjà tout prêt, et ont même du vous expliquer ce qu'il y avait dedans !
Il se trouve ici : https://github.com/SII-Codelab-Chaos/Gatling-Load-Tests-FuSIIon
Ce dossier contient un scénario déjà tout prêt, pour le lancer :

    mvn gatling:test
    
Ce scénario va lancer des requêtes sur nos micro services, et nous produire un rapport.
Une fois le test terminé, vous n'avez plus qu'a suivre le lien et ouvrir le rapport.

## Conclusion et debriefing

Victoire ! Il ne vous reste plus qu'a débriefé les résultats du test avec les Speaker et les autres participants.
Mais au fait, pourquoi est ce que tous les conteneurs sont KO...

