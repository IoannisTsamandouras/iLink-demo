CREATE TABLE user (
        id INT NOT NULL AUTO_INCREMENT,
        FIRSTNAME VARCHAR(30) NOT NULL,
	LASTNAME VARCHAR(30) NOT NULL,
        EMAIL VARCHAR(30) NOT NULL,
        AGE INT(3) NOT NULL,
	TIN INT(9) NOT NULL,
	STRNAME VARCHAR(100) NOT NULL,
        STRNUM INT(3) NOT NULL,
        POSTCODE VARCHAR(30) NOT NULL,
	CITY VARCHAR(30) NOT NULL,
        PRIMARY KEY (ID)
    );
