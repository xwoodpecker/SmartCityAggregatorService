# Aggregator-Service
Ein Cloud-Service, der Messungen aus einem SmartCity-Testfeld durch einen MQTT-Broker aggregiert und
über eine REST-Schnittstelle bereitstellt.

## Architektur

## Konfiguration

## Build
Der Maven-Buildprozess erstellt eine ausführbare `.jar` mit allen Abhängigkeiten sowie ein Docker-Image.
Es müssen folgende Abhängigkeiten auf dem Rechner installiert sein:
- [JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/get-started)

(todo: versionen?)

Anschließend kann der Buildprozess mit `mvn package` angestoßen werden. (Hinweis: Tests zum
aktuellen Zeitpunkt nicht ausführbar, daher `mvn package -DskipTests`)

Die ausführbare `.jar` befindet sich nach erfolgreichem Build im `/target`-Ordner. Das Docker-Image ist (todo).

## Deployment
todo: Anleitung zum Deployment auf einem Remote Host

## Schnittstellenspezifikation
Der Aggregator-Service ist konform mit der OpenAPI-Spezifikation und stellt unter dem Endpunkt
`/swagger-ui.html` eine graphische Oberfläche mit Details zur Spezifikation bereit. Desweiteren
können auf dieser Oberfläche sämtliche Endpunkte ausprobiert werden.

Die JSON-basierte OpenAPI-Spezifikation steht unter `/v3/api-docs` bereit.