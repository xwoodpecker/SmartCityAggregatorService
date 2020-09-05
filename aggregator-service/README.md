# Aggregator Service
Ein auf Spring Boot basierender Cloud-Service, der Messungen aus einem SmartCity-Testfeld durch einen MQTT-Broker aggregiert und
über eine REST-Schnittstelle bereitstellt.

## Architektur
todo

Stellen Sie die Architektur Ihres Projekts dar. Beginnen Sie mit einem Abschnitt zur Lösungsstrategie. D.h. eine kompakte Beschreibung der Kernidee und des Lösungsansatzes. Beschreiben Sie wichtige Designentscheidungen und Begründen Sie diese.

#### Use Cases / User Stories
todo

Beschreiben Sie Use Cases und/oder User Stories

#### Anforderungen
todo

Darstellung der Anfoderungen unterteilt nach funktionalen und nichtfunktionalen Anforderungen sowie nach Must-, Should-, und Could-Have Anfoderungen

###### Funktionale Anforderungen
todo

###### Nichtfunktionale Anforderungen
todo

#### Lösungsstrategie
todo

Geben Sie eine kompakte Beschreibung der Kernidee Ihres Lösungsansatzes. Begründen Sie wichtige Designentscheidungen. Z.B. die Wahl der Middleware, der Programmiersprache, des Architekturansatzes etc.

#### Statisches Modell

###### Bausteinsicht
todo

Die Bausteinsicht beschreibt die logischen Komponenten Ihres Systems und deren Zusammenwirken. Stellen Sie auch die eingesetzte Technologien, Frameworks etc. dar.


###### Verteilungssicht
todo

Die Verteilungssicht stellt dar, auf welchen physischen Rechnern die einzelnen Komponenten Ihres Systems ausgeführt werden und wie diese Verbunden sind.

###### Klassendiagramme
todo


###### API
Der Aggregator-Service ist konform mit der OpenAPI-Spezifikation und stellt unter dem Endpunkt
`/swagger-ui.html` eine graphische Oberfläche mit Details zur Spezifikation bereit. Desweiteren
können auf dieser Oberfläche sämtliche Endpunkte ausprobiert werden.

Die JSON-basierte OpenAPI-Spezifikation steht unter `/v3/api-docs` bereit.


#### Dynamisches Modell
todo

Beschreiben Sie den Ablauf Ihres Programms in Form von Aktivitäts- und oder Sequenzdiagrammen.


## Getting Started
Um dieses Projekt lokal aufzusetzen muss zuerst dieses Git-Repository geklont werden:
````
git clone https://github.com/htw-saar/SmartCityAggregatorService.git
````
Anschließend kann das Projekt in einer beliebigen IDE bearbeitet werden.

Der Buildprozess ist mit Maven realisiert. Wichtige Phasen:
- `mvn package`: Kompiliert das Projekt, erstellt eine ausführbare `.jar` mit allen benötigten Dependencies
- `mvn install`: Erstellt ein Docker-Image, welches eine JRE sowie die ausführbare `.jar` als Entry-Point enthält. Wird im lokalen Docker Repository abgelegt. Ein Docker-Agent muss lokal verfügbar sein, um diese Phase auszuführen.
- `mvn deploy`: Das generierte Docker-Image wird zu der in der `pom.xml` definierten Docker-Registry gepusht.

#### Vorraussetzungen
Es müssen folgende Abhängigkeiten auf dem Rechner installiert sein:
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/get-started) (für Maven Phasen `install`, `deploy`)

#### Installation und Deployment

Beschreiben Sie die Installation und das Starten ihrer Software Schritt für Schritt.

Das Docker-Image kann mit `docker run htw.smartcity/aggregator:1.0-SNAPSHOT` lokal ausgeführt werden.
todo: Anleitung zum Deployment auf einem Remote Host
todo: fix, outdated


## Built With
todo mehr?

* [Spring Boot](https://spring.io/projects/spring-boot)  - ?
* [Eclipse Paho](https://www.eclipse.org/paho/) - The MQTT Client used
* [Swagger](https://swagger.io/) / [springdoc](https://springdoc.org/) - OpenAPI compliant API specification
* [Maven](https://maven.apache.org/) - Dependency Management


## License
This project is licensed under the GNU General Public License v3.0

## Acknowledgments
todo
* [Prof. Dr. Markus Esch](https://www.htwsaar.de/htw/ingwi/fakultaet/personen/profile/markus-esch) - Projektbetreuung
* [Baeldung](https://www.baeldung.com/) - Große Auswahl an Spring Boot-fokusierten Guides
* [Stackoverflow](https://stackoverflow.com/) - :ok_man: