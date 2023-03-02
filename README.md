# Garbage
## Leverantör
Sundsvalls Kommun

## Beskrivning
Tillhandahåller information om när sophämtning sker på en viss adress eller inom ett visst område. 

## Tekniska detaljer

### Konfiguration

Konfiguration sker i filen `src/main/resources/application.properties` genom att sätta nedanstående properties till önskade värden:


|`spring.datasource.url`|
|`spring.datasource.username`|
|`spring.datasource.password`|
|Property|Beskrivning|
|---|---|
|`spring.datasource.url`| URL till databasen
|`spring.datasource.username`| Användarnamn för databasen
|`spring.datasource.password`| Lösenord för databasen

### Paketera och starta tjänsten

Paketera tjänsten som en körbar JAR-fil genom:

```
mvn package
```

Starta med:

```
java -jar target/api-service-garbage-<VERSION>.jar
```

### Bygga och starta tjänsten med Docker

Bygg en Docker-image av tjänsten:

```
mvn spring-boot:build-image
```

Starta en Docker-container:

```
docker run -i --rm -p 8080:8080 evil.sundsvall.se/ms-garbage:latest
```


Copyright &copy; 2022 Sundsvalls Kommun
