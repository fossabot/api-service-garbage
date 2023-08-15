# Garbage
## Leverantör
Sundsvalls Kommun

## Beskrivning
Tillhandahåller information om när sophämtning sker på en viss adress eller inom ett visst område. 

## Tekniska detaljer

### Konfiguration

Konfiguration sker i filen `src/main/resources/application.properties` genom att sätta nedanstående properties till önskade värden:

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

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-garbage&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-garbage)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-garbage&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-garbage)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-garbage&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-garbage)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-garbage&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-garbage)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-garbage&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-garbage)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-garbage&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-garbage)

## 
Copyright (c) 2021 Sundsvalls kommun
