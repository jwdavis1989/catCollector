DROP TABLE IF EXISTS cats;
DROP TABLE IF EXISTS owners;
CREATE TABLE owners (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	isAdmin BOOLEAN,
	sessionData VARCHAR(255)
);

DROP TABLE IF EXISTS cats;
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
VALUES ('Gadget', 'Jeremy', 'white', 'siamese', '/resources/images/cat_white');

INSERT INTO cats (name, ownerName, color, breed, imageURL)
VALUES ('Sprocket', 'Jeremy', 'brown', 'tabby', '/resources/images/cat_brown');

INSERT INTO cats (name, ownerName, color, breed, imageURL)
VALUES ('Freya', 'Dan', 'unknown', 'unknown', '/resources/images/cat_white');

INSERT INTO cats (name, ownerName, color, breed, imageURL)
VALUES ('Garfield', 'Tyler', 'orange', 'fat cat', '/resources/images/cat_orange');



