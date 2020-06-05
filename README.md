1. bbdd mysql 'db_favorites' utf8_unicode_ci

create database db_favorites; -- Creates the new database

create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user

grant all on db_favorites.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database


'application.properties'

spring.jpa.hibernate.ddl-auto=create-drop -- update para conservar los datos


2. Run as > Spring Boot app
 

Swagger

http://localhost:8080/v2/api-docs
	
http://localhost:8080/swagger-ui.html



docs:

https://spring.io/guides/tutorials/rest/
