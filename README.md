# Regional Meisterschaft Skill 9

## rm-skills-9 starten

Das rm-skills-9 lässt sich ganz einfach starten. Es muss lediglich Java 17 installiert sein. Es ist nicht nötig, Node oder NPM selbst zu installieren. Das Build Script macht das für uns.

```shell
./gradlew :frontend:npmStart
./gradlew bootRun
```

Damit startet man die Angular App und den Spring Boot Server.

Die App ist dann unter http://localhost:4200/ und das Backend unter http://localhost:8080/ erreichbar.

Es besteht ebenfalls die Möglichkeit das Frontend und Backend im IntellJ über die Run-Configuration `Start rm-skills-9` zu starten.

Hat man den Verdacht, dass die kompilierten Dateien kaputt sind, lässt sich das ganze möglicherweise mit folgendem Befehl lösen:

```shell
./gradlew clean
```

## Datenbank-Modell neu generieren

Will man das Datenbank-Modell anpassen, muss man ein weiteres SQL-File im Ordner `backend/db-model/src/main/resources/db/migration` hinzufügen. Danach muss man folgenden Befehl ausführen, damit die jOOQ-Klassen neu generiert werden:

```shell
./gradlew backend:db-model:assemble
```

## Datenbank-Setup

Es wird eine lokale Mariadb Instanz benötigt.
Falls man Mariadb nicht lokal installieren möchte, kann man auch die Datenbank als Container mit docker-compose starten:
```shell
cd devops
docker-compose up RM-Skills-9-db
```
Dabei muss beachtet werden, dass im `./devops`-Verzeichnis ein Ordner `postgres-data` exisiteren muss.

