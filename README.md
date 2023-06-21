# A company producing screws and nuts

## Description

### The Spring Boot project provides a REST API for the administration of a company that produces screws and nuts.

* Private endpoints can be accessed by registered users (admins) after logging in, who can achieve the following:
* Adding a new customer, querying existing customers' orders.
* Adding a new order, deleting existing orders.
* Querying orders with close delivery deadlines.
* Adding a new material, querying existing materials by material quality and size (diameter).
* Transferring materials from an external warehouse to an internal warehouse.
* Assigning materials to an ordered HProduct, taking remaining material inventory.

## Entity-relationship diagram

![uml_digram_white](src/main/resources/static/ERDiagram.png)

## Used technologies
* Java
* Lombok
* Spring Boot
* Spring Data JPA
* Spring Security
* JUnit
* H2
* Flyway
* MySQL
* Docker
* Swagger

## Required technologies
* Docker
* MySQL

## Running
* Set up .env and .env.docker files in the project's main directory according to the sample files
* Create a database in MySQL
* Run the "docker compose up" command in the terminal
* Send queries according to the API specification

## Link
* [API Spec](http://localhost:8080/swagger-ui/index.html)

##
##

# Csavarokat és anyákat gyártó vállalkozás

## Leírás

### A Spring Boot projekt REST API-t biztosít egy csavarokat és anyákat gyártó vállalkozás adminisztrációjához.

* A privát végpontokat regisztrációt és belépést követően érik el a felhasználók (admin), akik a következőket tudják elérni:
* Új megrendelő (customer) felvétele, meglévő megrendelő megrendeléseinek lekérdezése.
* Új megrendelés (order) felvétele, meglévő megrendelés törlése.
* Közeli szállítási határidejű megrendelések lekérdezése.
* Új alapanyag (material) felvétele, meglévő alapanyagok lekérdezése anyagminőség és méret (átmérő) szerint.
* Alapanyagok átkönyvelése külső raktárból (warehouse) belső raktárba.
* Alapanyag megrendelt termékhez (HProduct) való hozzárendelése, maradék alapanyag készletbe vétele.

## Entitás-kapcsolati diagram

![uml_digram_white](src/main/resources/static/ERDiagram.png)

## Alkalmazott technológiák
* Java
* Lombok
* Spring Boot
* Spring Data JPA
* Spring Security
* JUnit
* H2
* Flyway
* MySQL
* Docker
* Swagger

## Szükséges technológiák
* Docker
* MySQL

## Futtatás
* .env és .env.docker fájlok beállítása a példafájloknak megfelelően a project főkönyvtárában
* Adatbázis létrehozása a MySQL-ben
* A `docker compose up` parancs futtatása a terminálban
* Lekérdezések küldése az API specifikációnak megfelelően

## Link
* [API Spec](http://localhost:8080/swagger-ui/index.html)
