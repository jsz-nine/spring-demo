# Spring Demo 

This project is a sandbox for implementing the most popular and commonly used springboot libraries, 
with the purpose of getting to practice and get an understanding of how springboot is used idiomatically.

## Features that will be implemented, tested and eventually be supported through this project: 

 - [ ] **Spring IoC**
 - [ ] **Spring AOP - Aspect Oriented Programming**
 - [X] **Spring Data (JPA)**
 - [X] **Spring MVC Basics**
 - [X] **Spring MVC REST**
 - [ ] **Security**
 - [ ] **Testing**
 - [X] **Spring Boot Basics**
 - [ ] **Spring Boot auto configuration**
 - [X] **Spring Boot actuator**
 - [ ] **Speing Boot Testing**


## How to **RUN**

This project is dockerized using the builder-runner-pattern in order to increase the compatibility from building 
the application, to actually run it on any PC that has docker installed (not taking CPU architecture into consideration)

this bash script has been tested on Linux and MacOS (Not windows, as of yet)
```
chmod +x run.sh
./run.sh 
```
 ## or
 ```
bash ./run.sh
 
```                                                           
The script will require a password for the database, and will be injected into all three docker containers at runtime, 
and will not be printed or saved in your host environment - it is not escaping all characters correctly, 
so check out ***Relevant commands***, to make sure everything is running as it should.


**Endpoints**

This service has built-in openapi/swagger generation, is exposed on these URI's 
 - http://localhost:8085/swagger-ui/index.html
 - http://localhost:8080/openapi

The run script is testing if the actual webserver is up and running using the actuator endpoints, 
that are pre-implemented in springboot
 - http://localhost:8085/actuator/health


**Recommended**

Take a look into the run.sh, in order to get an understanding of what is actually being executed on your machine.
The script contains three applications being run. 

1. A JVM container running the actual springboot application
2. A Postgres:latest container for the database
3. A temporary runScript using postgres, that provisions database and schema's into the postgres DB, before springboot
initializes and provisions the tables and data.


**Relevant commands**

It should be re-runnable and clean up after itself, on every run, but it NO GUARANTEE
check your docker environment ```docker ps ``` & ``` docker images ``` to make sure everything is running or 
shutdown properly.


** Sources for Exam prepration **

https://github.com/davidarchanjo/spring-certified-developer-study-guide?tab=readme-ov-file#3-rest-concepts- 

