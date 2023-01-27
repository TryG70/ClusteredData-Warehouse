# progressSoft - ClusteredData - Warehouse

### Introduction

Develop data warehouse for Bloomberg to analyze FX deals

---

### Task description

One of customer stories is to accept deals details from and persist them into DB.


### Technologies

- Java
- Maven
- PostgreSQL
- Spring Boot
- Spring Data JPA

### Requirements

You need the following to build and run the application:

- [JDK 17](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.8.6](https://maven.apache.org) (This is the build tool used.)


## How to run Application(s)
### step 1 - clone project with Terminal from [here](https://github.com/TryG70/ClusteredData-Warehouse)

```
git clone git@github.com:TryG70/ClusteredData-Warehouse.git
```

### step 2 - move into the project directory
```
cd clusteredData-warehouse/
```

### step 3 - Open the clusteredData-wareHouse Folder in an IDE, As a maven Project.
 
```
mvn spring-boot:run
```


### step 6 - Generate the .jar files with Terminal

```
mvn clean install 
```
OR
```
./mvnw clean install
```


## PostMan Collection for Integration Tests.
- clusteredDataWarehouse [here](https://api.postman.com/collections/22955162-cec7d93a-5683-47d7-bafd-6486b31a0696?access_key=PMAT-01GQRB18J5DBPKZ4Y5Y9GK7NSA)


## Running The Tests with Maven

To run the tests with maven, you would need to run the maven command for testing, after the code has been compiled.
```
mvn <option> test
```

