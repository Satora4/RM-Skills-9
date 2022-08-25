# WM Tippspiel

## WM-Tippspiel starten

Das WM-Tippspiel lässt sich ganz einfach starten. Es muss lediglich Java 17 installiert sein. Es ist nicht nötig, Node oder NPM selbst zu installieren. Das Build Script macht das für uns.

```shell
./graldew npmStart
./gradlew bootRun
```

Damit startet man die Angular App und den Spring Boot Server.

Die App ist dann unter http://localhost:4200/ und das Backend unter http://localhost:8080/ erreichbar.

Es besteht ebenfalls die Möglichkeit das Frontend und Backend im IntellJ über die Run-Configuration `Start WM-Tippspiel` zu starten.

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

Es wird eine lokale Postges 14 Instanz benötigt.
Falls man Postgres nicht lokal installieren möchte, kann man auch die Datenbank als Container mit docker-compose starten:
```shell
docker-compose -f ./devops/docker-compose.yml start wm-tippspiel-db
```
Dabei muss beachtet werden, dass im `./devops`-Verzeichnis ein Ordner `postgres-data` exisiteren muss.

## Docker Images erstellen

```shell
./gradlew pushDockerImages
```
Dabei wird basierend auf dem aktuellen Stand für das Frontend und Backend jeweils ein Image in die Ergon Docker Registry gepushed.

## Deployment

Auf dem Test-System muss folgendes ausgeführt werden:

```shell
cd /opt/wm-tippspiel-22
docker-compose down
docker-compose rm -f
docker-compose pull
docker-compose up --build -d
```

Es kann vorkommen, dass das Backend versucht auf die DB zu verbinden, bevor diese Verbindungen akzeptiert.
In diesem Fall reicht es den Backend-Container separat zu starten.
