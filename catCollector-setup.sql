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
	ownerName VARCHAR(255) NOT NULL,
	FOREIGN KEY(ownerName) REFERENCES owners(username),
	color VARCHAR(255) NOT NULL,
	breed VARCHAR(255) NOT NULL,
	imageURL VARCHAR(255) NOT NULL
);


INSERT INTO owners (username, password, isAdmin)
VALUES ('Jeremy', '123', true);

INSERT INTO owners (username, password, isAdmin)
VALUES ('Tyler', '123', true);

INSERT INTO owners (username, password, isAdmin)
VALUES ('Robert', '123', false);

INSERT INTO owners (username, password, isAdmin)
VALUES ('Stephen', '123', false);

INSERT INTO owners (username, password, isAdmin)
VALUES ('Dan', '123', false);

INSERT INTO cats (name, ownerName, color, breed, imageURL)
VALUES ('Gadget', 'Jeremy', 'White', 'Siamese', 'cat_white');

INSERT INTO cats (name, ownerName, color, breed, imageURL)
VALUES ('Sprocket', 'Jeremy', 'Brown', 'Tabby', 'cat_brown');

INSERT INTO cats (name, ownerName, color, breed, imageURL)
VALUES ('Freya', 'Dan', 'white', 'Unknown', 'cat_white');



