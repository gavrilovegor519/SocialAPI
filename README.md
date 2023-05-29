# SocialAPI

My social network API server. Spring Boot/MVC/Data JPA/Security, PostgreSQL, Java 17, Maven, Docker/Podman.

PostgreSQL Docker/Podman setup: https://github.com/khezen/compose-postgres

Launch PostgreSQL server with Docker: ```docker-compose up -d```.

Launch PostgreSQL server with Podman: ```podman-compose up -d``` (only on Fedora Linux with installed
package ```podman-compose```).

Build and run project: ```./mvnw spring-boot:run```.

Only build project: ```./mvnw package```.

Also you able to build project from Intellij IDEA Ultimate and Eclipse with Spring's Eclipse plugin.