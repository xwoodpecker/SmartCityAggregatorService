# Aggregator Service
Ein auf Spring Boot basierender Cloud-Service, der Messungen aus einem SmartCity-Testfeld durch einen MQTT-Broker aggregiert und
über eine REST-Schnittstelle bereitstellt.
<!--
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
-->

###### API
Der Aggregator-Service ist konform mit der OpenAPI-Spezifikation und stellt unter dem Endpunkt
`/swagger-ui.html` eine graphische Oberfläche mit Details zur Spezifikation bereit. Desweiteren
können auf dieser Oberfläche sämtliche Endpunkte ausprobiert werden.

Die JSON-basierte OpenAPI-Spezifikation steht unter `/v3/api-docs` bereit.

<!--
#### Dynamisches Modell
todo

Beschreiben Sie den Ablauf Ihres Programms in Form von Aktivitäts- und oder Sequenzdiagrammen.
-->

## Getting Started
todo git clone etc.

Der Maven-Buildprozess erstellt eine ausführbare `.jar` mit allen Abhängigkeiten sowie ein Docker-Image.

Anschließend kann der Buildprozess mit `mvn package` angestoßen werden. (Hinweis: Tests zum
aktuellen Zeitpunkt nicht ausführbar, daher `mvn package -DskipTests`)

Die ausführbare `.jar` befindet sich nach erfolgreichem Build im `/target`-Ordner. Das Docker-Image wird
in einer private Docker-Registry gespeichert.

todo: sollte nicht in package passieren, sondern in install

#### Vorraussetzungen
Es müssen folgende Abhängigkeiten auf dem Rechner installiert sein:
- [JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/get-started)

(todo: versionen?)

#### Installation und Deployment

Beschreiben Sie die Installation und das Starten ihrer Software Schritt für Schritt.

Das Docker-Image kann mit `docker run htw.smartcity/aggregator:1.0-SNAPSHOT` lokal ausgeführt werden.
todo: Anleitung zum Deployment auf einem Remote Host
todo: fix, outdated

<!--
## Built With
todo

Geben Sie an, welche Frameworks und Tools Sie verwendet haben. Z.B.:

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds
-->

## License

This project is licensed under the GNU General Public License v3.0

<!--
## Acknowledgments
todo

* Hat tip to anyone whose code was used
* Inspiration
* etc
-->