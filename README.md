# WM Tippspiel

## WM-Tippspiel starten

Das WM-Tippspiel lässt sich ganz einfach starten. Es muss lediglich Java 17 installiert sein. Ist es NICHT nötig, Node oder NPM selbst zu installieren. Das Build Script macht das für uns.

```shell
./gradlew bootRun
```

Damit startet man den Spring Boot Server, der ebenfalls die Angular App beinhaltet (siehe Ordner `frontend`).

Die App ist dann unter http://localhost:8080/ erreichbar.

Hat man den Verdacht, dass die kompilierten Dateien kaputt sind, lässt sich das ganze möglicherweise mit folgendem Befehl lösen:

```shell
./gradlew clean
```