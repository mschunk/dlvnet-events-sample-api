# Beispiel einer Schnittstelle für DLV.net Events

Dieses Beispiel zeigt eine Schnittstelle wie ein Fremdsystem (wie LADV) Events in DLV.net erstellen, aktualiseren, löschen und abrufen könnte.

Die REST Schnittstelle dieses Beispiels ist voll funktionsfähig - ca. 75 Zeilen Quellcode. Auf das speichern in einer Datenbank und die Validierung wurde im Beisiel verzichtet - diese Teile sind in DLV.net bereits vorhanden.

Quellcode:
https://github.com/mschunk/dlvnet-events-sample-api/blob/952bc11d39f41a693bf8751c5c5a1d28843f08df/grails-app/controllers/dlvnet/events/sample/api/DlvnetController.groovy#L6

Beispielanwendung läuft hier:
http://dlvnet-events-sample-api.eu-de.mybluemix.net

## Vorbereitung

Quellcode für die Beispieldatei abrufen:
```
git clone https://github.com/mschunk/dlvnet-events-sample-api.git
cd dlvnet-events-sample-api
cat sample.xml
```

Im aktuellen Verzeichnis befindet sich eine sample.xml

## Eine Veranstaltungsanmeldung (Event) erstellen

``` 
curl -i -d @sample.xml -H "Content-Type: text/xml; charset=utf-8" -X POST http://dlvnet-events-sample-api.eu-de.mybluemix.net/dlvnet/events
> HTTP/1.1 201 Created
```

erstellen einer bereits vorhandenen Veranstaltung:
```
curl -i -d @sample.xml -H "Content-Type: text/xml; charset=utf-8" -X POST http://dlvnet-events-sample-api.eu-de.mybluemix.net/dlvnet/events
> HTTP/1.1 400 Bad Request
event id '17V02000000000311' already exist.
```

## Eine Veranstaltungsanmeldung (Event) abrufen

``` 
curl -i -X GET http://dlvnet-events-sample-api.eu-de.mybluemix.net/dlvnet/events/17V02000000000311
> HTTP/1.1 200 OK
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Events>
...
```

## Eine Veranstaltungsanmeldung (Event) aktualisieren

``` 
curl -i -d @sample.xml -H "Content-Type: text/xml; charset=utf-8" -X PUT http://dlvnet-events-sample-api.eu-de.mybluemix.net/dlvnet/events/17V02000000000311
> HTTP/1.1 200 OK
```


## Eine Veranstaltungsanmeldung (Event) löschen

``` 
curl -i -X DELETE http://dlvnet-events-sample-api.eu-de.mybluemix.net/dlvnet/events/17V02000000000311
> HTTP/1.1 200 OK
```

löschen einer nicht vorhandenen Veranstaltung:
``` 
curl -i -X DELETE http://dlvnet-events-sample-api.eu-de.mybluemix.net/dlvnet/events/17V02000000000311
> HTTP/1.1 404 Not Found
event id '17V02000000000311' does not exist.
```
