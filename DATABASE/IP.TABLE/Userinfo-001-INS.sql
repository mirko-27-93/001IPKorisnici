DROP DATABASE IF EXISTS ip; 
CREATE DATABASE ip; 
USE ip; 

CREATE TABLE userinfo(
	username VARCHAR(45) NOT NULL UNIQUE,  
    firstname VARCHAR(45), 
    secondname VARCHAR(45), 
    emailaddress VARCHAR(100), 
    passwordhash VARCHAR(200) NOT NULL, 
    country VARCHAR(200), 
    city VARCHAR(200), 
    telephone VARCHAR(200)
); 