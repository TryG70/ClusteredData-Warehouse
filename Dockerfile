FROM openjdk:17-jdk-alpine

COPY target/clusteredData-warehouse-0.0.1-SNAPSHOT.jar clusteredData-warehouse-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/clusteredData-warehouse-0.0.1-SNAPSHOT.jar"]