DROP TABLE IF EXISTS Owners;
CREATE TABLE owners (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	isAdmin BOOLEAN
);

DROP TABLE IF EXISTS Cats;
CREATE TABLE cats (
	UID SERIAL PRIMARY KEY,-- Serial is basically the same as an INT, but with auto incrementing abilities
	name VARCHAR(255) NOT NULL,
	FOREIGN KEY ownerName VARCHAR(255) REFERENCES owners(username),
	color VARCHAR(255) NOT NULL,
	breed VARCHAR(255) NOT NULL,
	imageURL VARCHAR(255) NOT NULL
);

