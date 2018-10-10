# coderepo
This is a sample RESTful Api project developed with SpringBoot 2.
This project hadles simple CRUD operation with a JSON object

Technology Stack
================
1. Java 8
2. Spring Boot 2.0
3. Maven

Prerequisite
============
1. Install Java 8 and Apache Maven 3
2. Any API testing tool 

To Run the Project
==================
1. Download the Project
2. Update the config.properties file from the 'resources' folder to change the folder name and file

    a. The folder and file will be created during the startup of the project
    
    b. This file will be used for the sample data CRUD operation
  
3. Run "mvn package" command from the command prompt

    a. This will generate the ".war" file
  
4. Deploy the generate war file in a web server

5. Open the API Testing tool, and hit the url

    a. GET - To get all the available employee data
     RequestMethod should be GET
     
     URL : http://localhost:8080/corp-1/employee/
     
    b. GET - To get the filtered by Age data
    RequestMethod should be GET
    
     URL : http://localhost:8080/corp-1/employee/filterByAge?operator=lt&value=23&sort=asc

    c. POST - To post a new data
    RequestMethod should be POST
    
     URL : http://localhost:8080/corp-1/employee/
     
     Sample Data: in JSON Format
     {
        "id": 6,
        "fullName": "Test",
        "age": 30,
        "salary": 20000
    }
    
    d. PUT - To update the existing data
    RequestMethod should be PUT
    
     URL : http://localhost:8080/corp-1/employee/
     
     Sample Data: in JSON Format (which needs to be udpated)
     {
        "id": 6,
        "fullName": "New",
        "age": 30,
        "salary": 20000
    }
    
    e. DELETE - To delete the existing data
    RequestMethod should be PUT
    
     URL : http://localhost:8080/corp-1/employee/
     
     Sample Data: in JSON Format (which needs to be deleted)
     {
        "id": 6
    }
