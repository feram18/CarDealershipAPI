# Car Dealership REST API
>Towson University | Fall 2020 | COSC 457 - Database Management Systems Project

## About the Project
A Dockerized Spring application for a (fictional) car dealership to manage staff and customers' information, vehicle
inventory, service records, and among other data. Data is persisted on a PostgreSQL database hosted in Heroku.
It includes integration testing for the controller layer, and unit tests and entity to DTO mappers.

The course project was to be created with plain Java, and the Java Swing framework for the GUI. I took the opportunity 
to learn about the Spring Framework and used the project scenario to rebuild the application.

Read the API documentation at `localhost:8080/swagger-ui/#/`.

### Technologies/Frameworks Used
- Java 11
- Spring Framework
- Apache Maven
- IntelliJ
  
**Database**
- PostgreSQL 13
  
**Testing**
- JUnit5
- Mockito
- Postman

**Deployment**
- Docker
- AWS' EC2

Among several other libraries/dependencies which can be found on `pom.xml`.