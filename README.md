Previo a arrancar el proyecto se ha de preparar una base de datos mysql

una opción es instalar XAMPP y arrancar los servicios Apache y MySQL
y crear una bbdd mysql 'db_favorites' utf8_unicode_ci

create database db_favorites; -- Creates the new database
create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
grant all on db_favorites.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database
#spring.jpa.hibernate.ddl-auto=create-drop --creará las tablas y las borrará al cerrar, update para conservar la info

