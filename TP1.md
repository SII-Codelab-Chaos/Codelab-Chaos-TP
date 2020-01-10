# TP1

"Un stagiaire a touché au code source. Depuis, l'application bugue !!
Pourtant, les tests unitaires passaient au vert ! Et la couverture de tests est à 100% sur l'ensemble de notre code métier !
A un mois de la MEP, doit t'on blâmer le stagiaire ou l'auteur des tests unitaires ?
Comment aurait-on pu éviter d'avoir des TU inutiles ?"

## Objectifs

* Mutation testing : théorie et pratique
* Pit Test :
  * installation et utilisation de l’outil
  * analyse
  * correction de TUs en suivant les résultats des analyses
* Descartes et extreme mutation

## Tests unitaires et couverture de code

### Basculement vers la branche GIT "TP1"

```shell
git checkout TP1
```

### Aller dans le nouveau répertoire "TP1-fusiion-gestion-competences"

```shell
cd TP1-fusiion-gestion-competences
```

### Lancer les test unitaires

```shell
mvn clean test
```

Résultat attendu :

```shell
Results :
Tests run: 251, Failures: 0, Errors: 0, Skipped: 0
```

### Ajouter la dépendance pour Jacoco

> Note : Jacoco permet de vérifier la couverture de code par les TU

Dans le `pom.xml` du projet, dans `<build></plugins>`, rajouter les lignes suivantes :

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.5</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Tester la couverture de code

```shell
mvn clean test
```

JaCoCo publie un rapport dans le dossier `/target/site/jacoco` du projet.
> Nous nous intéresserons aux résultats de la classe `CompetenceService`

## Mutation testing

### Ajout de la dépendance pour Pit Test

Dans le `pom.xml` du projet, dans `<build><plugins>`, rajouter les lignes suivantes :

```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.4.10</version>
 </plugin>
```

### mutationCoverage

Lancer un build de votre application (pour compiler les sources) :
```shell
mvn install
```

Lancer l'outil de mutation :

```shell
mvn org.pitest:pitest-maven:mutationCoverage
```
 
> Si l'outil prend trop de temps :
> 
> Dans le `pom.xml` du projet, dans `<plugin>` :
> ```xml
><configuration>
>    <threads>5</threads>
></configuration>
>```

> En cas de doute sur les `TIMED OUT` (mais attention, ça peut prendre du temps !) :
> 
> Dans le `pom.xml` du projet, dans `<plugin>` :
> ```xml
><configuration>
>    <timeoutConstant>50</timeoutConstant>
></configuration>
>```

PitTest publie un rapport dans le dossier `/target/pit-reports/<date-heure>` du projet.
> Qu'est-ce qui est intéressant dans ce résultat ? :)

## Conclusion et debriefing
