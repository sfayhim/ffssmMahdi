# Tests Unitaires FFSSM

## Vue d'ensemble

Cette suite de tests unitaires couvre l'ensemble des classes du projet FFSSM (Fédération Française de Sports Sous-Marins). Les tests sont écrits avec **JUnit 5** et visent à valider le comportement de toutes les classes métier.

## Résultats des Tests

✅ **67 tests unitaires** - Tous réussis
- **ClubTest**: 11 tests
- **DiplomeDeMoniteurTest**: 9 tests
- **EmbaucheTest**: 9 tests
- **LicenceTest**: 9 tests
- **PlongeeTest**: 12 tests
- **PlongeurTest**: 9 tests
- **SiteTest**: 8 tests

## Structure des Tests

### 1. LicenceTest
Tests pour la classe `Licence` :
- ✅ Validation de la construction d'une licence
- ✅ Vérification de la validité d'une licence (jour de délivrance, pendant l'année, dernier jour)
- ✅ Détection des licences expirées (après un an, avant délivrance)
- ✅ Gestion des cas limites (dates null, licence sans date)
- ✅ Tests des setters et getters

### 2. PlongeurTest
Tests pour la classe `Plongeur` :
- ✅ Constructeur simple et complet
- ✅ Ajout de licences (simple, multiple, null)
- ✅ Récupération de la dernière licence (sans licence, une licence, plusieurs licences)
- ✅ Tri des licences par date de délivrance

### 3. ClubTest
Tests pour la classe `Club` :
- ✅ Construction et gestion des propriétés du club
- ✅ Organisation de plongées (simple, multiple, null)
- ✅ Détection des plongées non conformes :
  - Sans plongées
  - Avec licences valides
  - Avec licences expirées
  - Avec plongeurs sans licence
  - Scénarios mixtes (conformes et non conformes)

### 4. PlongeeTest
Tests pour la classe `Plongee` :
- ✅ Construction d'une plongée
- ✅ Ajout de participants (simple, multiple, null, doublons)
- ✅ Vérification de la conformité :
  - Sans participants
  - Avec licences valides
  - Avec licences expirées
  - Avec plongeurs sans licence
  - Avec plusieurs licences par plongeur

### 5. DiplomeDeMoniteurTest
Tests pour la classe `DiplomeDeMoniteur` :
- ✅ Construction du diplôme
- ✅ Gestion de l'employeur actuel :
  - Sans embauche
  - Avec embauche en cours
  - Avec embauche terminée
- ✅ Gestion de plusieurs embauches successives
- ✅ Changement d'employeur

### 6. EmbaucheTest
Tests pour la classe `Embauche` :
- ✅ Construction et getters
- ✅ Détection d'une embauche terminée ou en cours
- ✅ Terminaison d'une embauche
- ✅ Modification de la date de fin
- ✅ Cas limites (date fin null)

### 7. SiteTest
Tests pour la classe `Site` :
- ✅ Construction avec différents types de données
- ✅ Setters et getters
- ✅ Méthode toString
- ✅ Noms longs et caractères spéciaux
- ✅ Equals et hashCode (générés par Lombok)

## Couverture de Code

Le projet utilise **JaCoCo** pour mesurer la couverture de code. Le rapport est généré dans :
```
target/site/jacoco_test/index.html
```

## Exécuter les Tests

### Tous les tests
```bash
mvn test
```

### Tests avec rapport de couverture
```bash
mvn clean verify
```

### Test d'une classe spécifique
```bash
mvn test -Dtest=LicenceTest
```

## Points de Validation Clés

### Conformité des Plongées
Les tests valident rigoureusement la règle métier principale :
> Une plongée est conforme si **tous les plongeurs** ont une licence **valide** à la date de la plongée.

Cas testés :
- ✅ Plongée sans participants → conforme
- ✅ Tous avec licences valides → conforme
- ❌ Au moins un sans licence → non conforme
- ❌ Au moins une licence expirée → non conforme

### Validité des Licences
Les tests vérifient la règle de validité :
> Une licence est valide pendant **un an** à compter de sa date de délivrance.

Cas testés :
- ✅ Date = délivrance → valide
- ✅ Date ≤ délivrance + 1 an → valide
- ❌ Date > délivrance + 1 an → invalide
- ❌ Date < délivrance → invalide

### Gestion des Embauches
Les tests vérifient que :
- Un moniteur peut avoir plusieurs embauches
- L'employeur actuel est le dernier employeur si l'embauche n'est pas terminée
- Si toutes les embauches sont terminées, il n'y a pas d'employeur actuel

## Bonnes Pratiques Appliquées

1. **Nomenclature claire** : Les noms de tests décrivent précisément ce qui est testé
2. **Arrange-Act-Assert** : Structure claire de chaque test
3. **Isolation** : Chaque test est indépendant grâce à `@BeforeEach`
4. **Couverture complète** : Cas normaux, limites et erreurs
5. **Messages d'assertion** : Messages explicites en cas d'échec
6. **Null safety** : Tests des comportements avec valeurs null

## Dépendances

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

## Auteur

Tests créés conformément aux spécifications UML du projet FFSSM.
