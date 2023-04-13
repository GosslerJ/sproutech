# Csavarokat és anyákat gyártó vállalkozás

## Leírás

### A Spring Boot projekt REST API-t biztosít egy csavarokat és anyákat gyártó vállalkozás működését segítve.

* A privát végpontokat regisztrációt és belépést követően érik el a felhasználók (admin), akik a következőket tudják elérni:
* Új megrendelő (customer) felvétele, meglévő megrendelő megrendeléseinek lekérdezése.
* Új megrendelés (order) felvétele, meglévő megrendelés törlése.
* Közeli szállítási határidejű megrendelések lekérdezése.
* Új alapanyag (material) felvétele, meglévő alapanyagok lekérdezése anyagminőség és méret (átmérő) szerint.
* Alapanyagok átkönyvelése külső raktárból (warehouse) belső raktárba.
* Alapanyag megrendelt termékhez (product) való hozzárendelése, maradék alapanyag készletbe vétele.

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
