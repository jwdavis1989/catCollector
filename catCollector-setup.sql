DROP TABLE IF EXISTS owners;
CREATE TABLE owners (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	admin BOOLEAN
);

DROP TABLE IF EXISTS cats;
CREATE TABLE cats (
	UID SERIAL PRIMARY KEY,-- Serial is basically the same as an INT, but with auto incrementing abilities
	name VARCHAR(255) NOT NULL,
	ownerName VARCHAR(255) NOT NULL,
	color VARCHAR(255) NOT NULL,
	breed VARCHAR(255) NOT NULL,
	imageURL VARCHAR(255) NOT NULL
);

