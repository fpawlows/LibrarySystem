# Spring Boot Starter Project

Initial project setup for Spring Boot Web-Application using Thymeleaf.

## Recommended Development Environment

   * Eclipse
   * Maven


## Basic Project Setup

   * Spring Boot Starter Web
   * Spring Boot Starter Tomcat
   * Spring Boot Starter Thymeleaf
   * HTML5 Boilerplate
   * Embedded H2 for session management
   * H2 database for persisting application data

### how to make your data persitent across spring-boot restarts

   * By default application data is stored in in-memory database
   * to persist your data, the following line within application.properties need to be changed from
      * myapp.datasource.url=jdbc:h2:mem:myappdb;
   * to
      * myapp.datasource.jdbcurl=jdbc:h2:file:/path2databasefile;

## Deployment

### Build
```bash
mvn package
```

Either deploy generated WAR File to Tomcat or directly run using
```bash
mvn spring-boot:run

### or alternatively build and run in one command
mvn package; mvn spring-boot:run

```
### Connect
localhost:8080

## initialization code 
e.g. for creating entities for testing, as the configured h2 database is in-memory only.
```
at.ac.fhsalzburg.swd.spring.startup.CommandLineAppStartupRunner.run
```

## connect to local database using h2-console (browser)
```
http://localhost:8080/h2-console
```
