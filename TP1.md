# TP1

"Un stagiaire a touché au code source. Depuis, l'application bugue !!
Pourtant, les tests unitaires passaient au vert ! Et la couverture de tests est à 100% sur l'ensemble de notre code métier !
A un mois de la MEP, doit t'on blâmer le stagiaire ou l'auteur des tests unitaires ?
Comment aurait-on pu éviter d'avoir des TU inutiles ?"

## Objectifs

* Mutation testing : théorie et pratique
* PIT :
  * installation et utilisation de l’outil
  * analyse
  * correction de TUs en suivant les résultats des analyses
* Descartes et extreme mutation

> Pour l'intégralité du TP, se placer dans le dossier "TP1-fusiion-gestion-competences"

## Tests unitaires et couverture de code

### Lancer les test unitaires

```shell
mvn test
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
mvn test
```

JaCoCo publie un rapport dans le dossier `/target/site/jacoco` du projet.
> Nous nous intéresserons notamment aux résultats de la classe `CompetenceService`  

> Note : la colonne "Missed Branches" de ce rapport désigne les embranchements possibles du fait de la présence des structures if / switch . En effet, s'il est intéressant de connaitre la couverture de code en pourcentage de lignes ou d'instructions couvertes, il est tout aussi important de vérifier qu'un maximum d'"embranchements" sont pas couverts par les TU.

## Mutation testing

### Ajout de la dépendance pour PIT

Dans le `pom.xml` du projet, dans `<build><plugins>`, rajouter les lignes suivantes :

```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.4.10</version>
 </plugin>
```

Vous pouvez rajouter modifier la configuration de PIT pour accélérer l'analyse.  
Une valeur comprise entre 1 et le nombre de CPUs de votre machine est recommandé.  
Dans le `pom.xml` du projet, dans `<plugin>` (org.pitest) :
```xml
<configuration>
    <threads>4</threads>
</configuration>
```

### Outil de mutation coverage

Lancer un build de votre application (pour s'assurer d'avoir compilé les sources, PIT effectuant ses mutations sur le code compilé) :
```shell
mvn clean install
```

Lancer l'outil de couverture de mutation :

```shell
mvn org.pitest:pitest-maven:mutationCoverage
```

> Note : Nous commenterons ensemble la présence de nombreux **TIMED_OUT** lors de l'analyse par PIT

PIT publie un rapport dans le dossier `/target/pit-reports/<date-heure>` du projet.
> Qu'est-ce qui est intéressant dans ce résultat ?  
> Comparez notamment les différences de ce résultat avec le rapport JaCoCo.

Si vous souhaitez relancer l'outil, vous pouvez limiter la durée de l'analyse en la limitant à la classe CompetenceService et sa classe de TU associée.  
Dans le `pom.xml` du projet, dans `<plugin>` (org.pitest) :
```xml
<targetClasses>
    <param>fr.sii.atlantique.fusiion.fusiion_gestion_competences.services.CompetenceService</param>
</targetClasses>
<targetTests>
    <param>fr.sii.atlantique.fusiion.fusiion_gestion_competences.TestCompetenceService</param>
</targetTests>
```

### Encore un peu de temps devant vous ?

* Explorez les résultats du rapport d'analyse de PIT
* Localisez un mutant qui a survécu et détruisez-le !
  * Corrigez le TU correspondant (ou créez en un nouveau)
  * Relancez votre TU pour s'assurer qu'il soit OK
  * Relancez une analyse de PIT
  * Constatez que le mutant est bien tué et que vous avez amélioré la situation

> Utilisez la commande suivante pour accélérer

### Encore du temps devant vous ? Testez l'EXTREME MUTATION !

> Avec l'Extreme Mutation, plutôt que de générer une multitude de mutants pour chaque opérateur et condition, c'est toute la logique d'une méthode couverte par un test qui est supprimée. Tout le code de la méthode est remplacé par un simple retour (null, vide, ou void). Avec cette approche, un plus petit nombre de mutants est généré, ce qui simplifie grandement l'analyse des résultats de PIT dans un premier temps (par exemple quand vous testerez la première fois le mutation testing sur vos propres projets !)

Ajoutez la configuration suivante dans le `pom.xml` du projet, dans `<plugin>` (org.pitest) :

```xml
<configuration>
    <mutationEngine>descartes</mutationEngine>
</configuration>
<dependencies>
<dependency>
    <groupId>eu.stamp-project</groupId>
    <artifactId>descartes</artifactId>
    <version>1.2.5</version>
</dependency>
</dependencies>
```

Lancez la commande suivante :

```shell
mvn clean package
mvn org.pitest:pitest-maven:mutationCoverage -DmutationEngine=descartes
```

## Conclusion et debriefing
