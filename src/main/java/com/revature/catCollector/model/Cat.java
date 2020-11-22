package com.revature.catCollector.model;

public class Cat{ //implements Serializeable
	private int UID;
	private String name;
	private String ownerName;	//Equivalent to Owner.name
	private String color;
	private String breed;
	private String imageURL;
	
	public Cat() {
		super();
	}

	public Cat(int uID, String name, String ownerName, String color, String breed, String imageURL) {
		super();
		UID = uID;
		this.name = name;
		this.ownerName = ownerName;
		this.color = color;
		this.breed = breed;
		this.imageURL = imageURL;
	}


	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public String getname() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getownerName() {
		return ownerName;
	}
	public void setownerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public String toString() {
		return "Cat [UID=" + UID + ", name=" + name + ", ownerName=" + ownerName + ", color=" + color + ", breed="
				+ breed + ", imageURL=" + imageURL + "]";
	}

	
}