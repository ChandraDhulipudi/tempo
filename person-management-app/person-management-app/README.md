# Person-Management-App

#Structure:
    ·      It needs to be in GIT  (github or gitlab)*
    ·      It needs to be a multi-module maven project
    ·      Maven needs to be used via the so called maven wrapper.
    ·      The application needs to work with the following Frameworks / Libraries / Languages:
                Spring Boot 2+
                Spring
                Jpa or Spring Data
                Java 8+ or Kotlin
                Database of choice but needs to be in docker via docker-compose
    
#Functionality:
    Persistence:
    ·    Person:
    ·         First name (unique in combination with last name)
    ·         Last name
    ·         Age
    ·         Date of birth
    ·         Current living address
 
    ·    API:
    ·         Create person REST endpoint (unsecure)
    ·         Get person REST endpoint (unsecure)
    ·         Update persons address REST endpoint (secure; basic authentication for role admin)
    ·         Test:          
    ·         Unit testing
    ·         Integration testing

#Solution

## How to use
    First, the server should be started as a Spring Boot project. The server can be started up via the following command:
    
    `mvn spring-boot:run`
    
    Secondly, the jar file of the project could be used in a Dockerised environment.
    
    `java -jar target/person-management-app-0.0.1-SNAPSHOT.jar`

## Using the service

### Via the UI
To access the web page to use the service, navigate to the root (`/`). 
For example: `http://localhost:8006/person-management-app/`

If the security is enabled, authentication should be required on the browser.
Then, `username: user` and `password: pass` should be entered. 

### Via Postman
Either PostMan or an equivalent applications can be used to use the service.

### Example Via cURL 
The endpoint can be used via `cURL` using the following command:
```bash
curl --user user:pass \
     -X POST -H "Content-Type: application/json" \
     -d '{"firstName":"abc","lastName":"abc", "age":"34", "dob":"05-11-1987", "cur_living_addr":"2045TY NL"}' \
     http://localhost:8006/person-management-app/api/persons
  ```
  
## Testing
The tests can be performed via following Maven command:

`mvn clean test`



# Contributors
Chandra Dhulipudi