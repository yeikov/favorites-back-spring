Previo a arrancar el proyecto se ha de preparar una base de datos mysql

una opción es instalar XAMPP y arrancar los servicios Apache y MySQL
y crear una bbdd mysql 'db_favorites' utf8_unicode_ci

create database db_favorites; -- Creates the new database
create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
grant all on db_favorites.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database

para que JPA cree las tablas a partir de las entidades de java en el archivo 'application.properties'
spring.jpa.hibernate.ddl-auto=create-drop --creará las tablas y las borrará al cerrar, update para conservar la info

en STS para arrancar, seleccionado el proyecto, Run as > Spring Boot app


para hacer uso de los endPoints:

curl localhost:8080/backoffice/user/add -d name=john -d city=barcelona

http://127.0.0.1:8080/backoffice/user/all
http://127.0.0.1:8080/backoffice/user/id?num=1
http://127.0.0.1:8080/backoffice/user/name?nom=john

Swagger

http://localhost:8080/v2/api-docs
	
http://localhost:8080/swagger-ui.html



docs:
https://spring.io/guides/tutorials/rest/