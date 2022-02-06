1. bbdd mysql 'db_favorites' utf8_unicode_ci
eg xammp


  create database db_favorites; -- Creates the new database

2. create user in db
  create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user

  grant all on db_favorites.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database


'application.properties'

  spring.jpa.hibernate.ddl-auto=create-drop -- update para conservar los datos

arrancar app
2. Run as > Spring Boot app
/ o en DemoApplication, run as java app 


http://localhost:8080/backend


Swagger

http://localhost:8080/v2/api-docs
	
http://localhost:8080/swagger-ui.html



docs:

https://spring.io/guides/tutorials/rest/
