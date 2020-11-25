# CatCollector:
## What is Cat Collector?
Cat Collector is a website that allows cat lovers to adopt new cats, and keep track of their roster of delightful kitties. 

Users will be able to choose the color of cat they want, represented as an image stored on-disk on the server.


## PostgreSQL Tables
### Owners
	username VARCHAR(255) PRIMARY KEY
	password VARCHAR(255) NOT NULL
	admin BOOLEAN
	sessionData VARCHAR(255)

### Cats
	UID SERIAL PRIMARY KEY
	name VARCHAR(255) NOT NULL
	ownerName VARCHAR(255) NOT NULL
	color VARCHAR(255) NOT NULL
	breed VARCHAR(255) NOT NULL
	imageURL VARCHAR(255) NOT NULL

## Methods:
1.	getAllCats				
2.	getAllCatsByOwner			
3.	getCatByUID				
4.	addNewCat				
5.	updateCatByUID					
6.	removeCat 				
7.	getAllOwners
8.	getOwnerByName			
9.	addNewOwner									
10.	updateOwnerByUsername
11.	removeOwnerByUsername

## Installation
Download the following to run locally:
- maven
- tomcat 8.5
- jdk8
- PostgreSQL

## Paths
### POST /cat
This will edit a Cat in the database based on id.

### POST /owner
This will edit a User in the database based on username.
Allows you to change the Admin rights of a user, as well as set or blank their sessionData.

### GET /cat
Returns all cats.

### Get /cat?owner=${owner}
Returns all cats owned by the specified owner.

### GET /cat?UID=${UID}
Returns a cat by their unique identification number.

### GET /owner
Returns all Users.

### GET /owner?username=${username}
Returns a user with a matching username.

### PUT /cat
Reads in a JSON object and returns the constructed cat.

### PUT /owner
Reads in a JSON object and returns the constructed owner.

### DELETE /cat?UID=${UID}
Delete the cat with the inputted unique identification number.

### DELETE /owner?username=${username}
Delete the cat with the inputted unique identification number.

## Tests
Run mvn test while within the catCollector folder.
