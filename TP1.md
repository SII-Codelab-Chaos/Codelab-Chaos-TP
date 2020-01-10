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

### Récupérer les sources depuis git

```shell
git clone https://github.com/SII-Codelab-Chaos/fusiion-gestion-competences.git
```

### Lancer les test unitaires

```shell
mv clean test
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
mv clean test
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
    <version>LATEST</version>
 </plugin>
```

### mutationCoverage

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

> En cas de doute sur les `TIMED OUT` :
> 
> Dans le `pom.xml` du projet, dans `<plugin>` :
> ```xml
><configuration>
>    <timeoutConstant>250</timeoutConstant>
></configuration>
>```

## Conclusion et debriefing
