package com.revature.catCollector.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.catCollector.dao.DatabaseCatDAO;
import com.revature.catCollector.model.Cat;
//import com.revature.catCollector.template.InsertCatTemplate;

public class CatService 
{
	
	private DatabaseCatDAO catDAO;
	
	public CatService() 
	{
		this.catDAO = new DatabaseCatDAO();
	}
	
	// Generally this constructor is for doing unit testing
	// You should basically mock the DatabaseCatDAO object, and then pass it into the CatService object
	// In order to simulate an actual database
	// So when you're unit testing, generally you should not actually be using the actual database, and instead you should
	// mock returns from the DAO layer to the service layer
	
	// Look into using Mockito
	// There should definitely be a lot of articles about mocking DAOs
	public CatService(DatabaseCatDAO catDAO) 
	{
		this.catDAO = catDAO;
	}
	
	public ArrayList<Cat> getAllCats() throws SQLException 
	{
		
		//Store the result for exception handling
		ArrayList<Cat> result = catDAO.getAllCats();
		if (result.isEmpty()) 
		{
			throw new SQLException("Get All Cats failed.");
		}
			return result;
	}
	
	public Cat getCatByUID(int uid)  throws SQLException 
	{		
		//Store the result for exception handling
		Cat result = catDAO.getCatByUID(uid);
		
		if (result == null) 
		{
			throw new SQLException("Get Cat by UID failed.");
		}
				
		return result;
	}
	
	public ArrayList<Cat> getAllCatsByOwner(String username) throws SQLException 
	{
		//Store the result for exception handling
		ArrayList<Cat> result = catDAO.getAllCatsByOwner(username);
		if (result.isEmpty()) 
		{
			throw new SQLException("Get All Cats By Owner failed.");
		}
		return result;
	}
	
	public Cat addNewCat(String name, String ownerName, String color, String breed) throws SQLException 
	{
		//Store the result for exception handling
		Cat newCat = catDAO.addNewCat(name, ownerName, color, breed);
		
		if (newCat == null) 
		{
			throw new SQLException("Adding new Cat failed.");
		}
		
		return newCat;
	}
	
	public boolean updateCatByUID(int targetUID, String newName, String newOwnerName, String newColor, String newBreed)  throws SQLException
	{
		//Store the result for exception handling
		boolean result = catDAO.updateCatByUID(targetUID, newName, newOwnerName, newColor, newBreed);
		if (!result) 
		{
			throw new SQLException("Updating Cat failed. 0 Rows affected.");
		}
		return result;
	}
	
	public boolean removeCatByUID(int targetUID) throws SQLException 
	{
		//Store the result for exception handling
		boolean result = catDAO.removeCatByUID(targetUID);
		if (!result) 
		{
			throw new SQLException("Remove Cat failed. 0 Rows affected.");
		}
		return result;
	}
}
