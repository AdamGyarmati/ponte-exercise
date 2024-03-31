# Ponte Exercise

### Program specifikáció
Backend:
- Spring Boot 3.2.4
- Java 17
- Spring Security - Basic Auth
- Liquibase
- Lombok

Adatbázis:
- PostgreSQL
- PostgreSQL testcontainers
- H2 database

Frontend:
- Angular 17.3.0
- TypeScript: 5.4.2

### A feladatról
Backend fejlesztőként a feladat backend fejlesztésére fektettem a hangsúlyt,
így a frontend sajnos nem készült el. De mivel időm engedte ezért kis frontend kódot is írtam Angularban. <br />
A programban 4 domain-t hoztam létre (AppUser, Address, PhoneNumber, AppUserRole). A fő domain az AppUser,
hozzá csatlakozik a többi entitás.
![Képernyőfotó 2024-03-31 - 3.04.41.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fqk%2Fvf0xc0wx0rd77hl1vxnlyz640000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_coln7X%2FK%C3%A9perny%C5%91fot%C3%B3%202024-03-31%20-%203.04.41.png)

Spring Security-t használok, Basic Auth-al.

A DTO osztályokban végzek input validációt, a szervíz osztályokban saját Exception-ket 
használok a helyes működés érdekében.

Végeztem unit és integration teszteket is. A controllerek, service-k, repository-k tesztlefedettsége 80% fölötti. <br/>
Az Integration teszt-hez Postgres docker testcontainer-t használok.

Adatbázisnak Postgres 15 alpine docker image-et használtam: <br/>
```docker pull postgres:15-alpine``` <br/>
```docker run --name ponte-postgres -e POSTGRES_PASSWORD=PontePostgresPW1! -d postgres:15-alpine```

Adatbázis verziókövető rendszernek Liquibase-t használtam.

### Program indítása
- A projekt főkönyvtárán a ```docker-compose up``` paranccsal indul a program. Készül egy 
backend container(```ponte-backend```), frontend container(```ponte-fronted```) és postgres adatbázis(```postgres_db```). <br/>
- A resources mappába bekészítettem egy postman collection-t.
- A program indulásakor lefut a DatabaseInitializer-ben megírt CommandLineRunner és 2 User-t belerak az adatbázisba. 
  - Admin User: ```Name: John Doe``` ```Password: test1234```
  - Sima User: ```Name: Jane Doe``` ```Password: test1234```


### Hátralevő fejlesztések
- Swagger-t nem tudtam működésre bírni, bent hagytam a dependency-t valamint a securityben a configját. Ha erre kaphatok visszajelzést, hogy hol a hiba azt megköszönöm.
- Backend és Frontend docker container nem kommunikál egymással. Talán docker miatt Cors hiba lehet. Sajnos a végére hagytam a conténerizációt így már nem maradt időm kijavítani.