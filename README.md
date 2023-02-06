# container-api
JWT Protected API, ready to use and add custom controllers.


This container provides an API protected with JWT mechanism (Stateless authentication / authorization) ready to be used.


Requisites: 
Java 8, A MySQL Server instance running, Gradle (You can just use the wrapper).



Install Java 8 and your MySQL Server;

Start your MySQL instance and create the database 'users'



Test:
1 - Import the container-api application on your IDE as an existing gradle project.

2 - Run com.container.ContainerApplication as a 'Spring Boot App'

3 - Try to GET localhost:8080/api/test, you should get an error code 401

4 - Perform a POST to localhost:8080/api/login using this JSON as the request body: { "user":"admin", "password":"admin" } and copy the token value

5 - Try to perform a GET to same URL as before but now send also the header: 'authorization' = {GIVEN_TOKEN}

6 - Feel free to add your custom auth configuration and controllers

