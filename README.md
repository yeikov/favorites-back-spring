# 'Favorites' spring back-app

## bbdd
Se requiere una base de datos:  e.g. XAMPP  
 
bbdd mysql 'db_favorites' utf8_unicode_ci  

	create database db_favorites; -- Creates the new database  
	create user in db

	create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user  
	grant all on db_favorites.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database

## iniciar la app
 Configuración  
'application.properties'

	spring.jpa.hibernate.ddl-auto=create-drop -- update para conservar los datos

Run as > Spring Boot app

o en DemoApplication, > run as java app 

### Ver despliegue  

	http://localhost:8080/backend


### Swagger

	http://localhost:8080/v2/api-docs
		
	http://localhost:8080/swagger-ui.html


### Docs:

https://spring.io/guides/tutorials/rest/
