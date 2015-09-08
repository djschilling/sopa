# Sopa #

* [Übersicht](https://github.com/pongo710/sopa/#%C3%9Cbersicht)
* [Spielmodi](https://github.com/pongo710/sopa/#spielmodi)
* [Lizenz](https://github.com/pongo710/sopa/#lizenz)
* [Entwicklung](https://github.com/pongo710/sopa/#entwicklung)

## Übersicht ##

Sopa ist ein Android-Puzzle-Spiel.

Das Spiel besteht aus einem Feld, das aus 4x4 Teilen besteht. Diese Teile beinhalten entweder eine Röhre, oder nichts.

Ziel des Spiels ist, mit den unterschiedlich gebogenen Röhren zwei Türen zu verbinden, die am Rand des Spiels sind.

Gesteuert werden die Röhren durch horizontale und vertikale Verschiebungen der Spalten und Zeilen des 4x4 Felds.

Sobald die Röhren die zwei Türen verbinden ist ein Level gelöst.

## Spielmodi ##

* [Levelmode](https://github.com/pongo710/sopa/#levelmode)

### Levelmode ###

Der Levelmode bietet vorgefertigte Level an die der Reihe nach durchgespielt werden können. Gelöste Level schalten immer ein weiteres frei.

Beim Lösen eines Levels werden eine Anzahl Sterne berechnet die man durch die Anzahl an gebrauchten Sielzüge verdient hat. Die maximale Sterneanzahl beträgt 3.

## Entwicklung ##

### Tests ###

Mit Android Studio 1.1 gibt es jetzt auch endlich Support für Android Unit Tests.

Tests ausführen:

```./gradlew test --continue```

Build ausführen:

```./gradlew build```

### Reporting ###

Über Jacoco lassen sich Reports über die Codeabdeckung erstellen. Dazu führt man folgenden Befehl aus:

```./gradlew jacocoTestReport```

Die erstellten HTML-Reports sind anschließend unter ```app/build/reports/jacoco/jacocoTestReport/html/index.html``` zu finden.

### Release erstellen ###

```./gradlew clean```
```./gradlew :app:assembleRelease```

Danach in Android Studio "Generate signed APK"

## Lizenz ##

[Sopa](https://github.com/pongo710/sopa) is licensed under the Apache License, Version 2.0. See [LICENSE](https://github.com/pongo710/sopa/blob/master/LICENSE.txt) for the full license text.