package com.revature.catCollector.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.catCollector.dao.DatabaseOwnerDAO;
import com.revature.catCollector.model.Cat;
import com.revature.catCollector.model.Owner;
//import com.revature.ownerCollector.template.InsertOwnerTemplate;

public class OwnerService 
{
	
	private DatabaseOwnerDAO ownerDAO;
	
	public OwnerService() 
	{
		this.ownerDAO = new DatabaseOwnerDAO();
	}
	
	// Generally this constructor is for doing unit testing
	// You should basically mock the DatabaseOwnerDAO object, and then pass it into the OwnerService object
	// In order to simulate an actual database
	// So when you're unit testing, generally you should not actually be using the actual database, and instead you should
	// mock returns from the DAO layer to the service layer
	
	// Look into using Mockito
	// There should definitely be a lot of articles about mocking DAOs
	public OwnerService(DatabaseOwnerDAO ownerDAO) 
	{
		this.ownerDAO = ownerDAO;
	}
	 
	public ArrayList<Owner> getAllOwners() throws SQLException 
	{
		
		//Store the result for exception handling
		ArrayList<Owner> result = ownerDAO.getAllOwners();
		if (result.isEmpty()) 
		{
			throw new SQLException("Get All Owners failed.");
		}
			return result;
	}
	public Owner getOwnerByUsername(String name)  throws SQLException
	{		
		//Store the result for exception handling
		Owner result = ownerDAO.getOwnerByUsername(name);
		
		if (result == null) 
		{
			throw new SQLException("Get Owner by Username failed.");
		}
				
		return result;
	}
	
	public Owner addNewOwner(String username, String password, Boolean isAdmin) throws SQLException 
	{
		//Store the result for exception handling
		Owner newOwner = ownerDAO.addNewOwner(username, password, isAdmin);
		
		if (newOwner == null) 
		{
			throw new SQLException("Adding new Owner failed.");
		}
		
		return newOwner;
	}

	
	public boolean updateOwnerByUsername(String targetUsername, String newPassword, Boolean newIsAdmin, String newSessionData) throws SQLException
	{
		//Store the result for exception handling
		boolean result = ownerDAO.updateOwnerByUsername(targetUsername, newPassword, newIsAdmin, newSessionData);
		if (!result) 
		{
			throw new SQLException("Updating Owner failed. 0 Rows affected.");
		}
		return result;
	}
	
	public boolean removeOwnerByUsername(String targetName) throws SQLException 
	{
		//Store the result for exception handling
		boolean result = ownerDAO.removeOwnerByUsername(targetName);
		if (!result) 
		{
			throw new SQLException("Remove Owner failed. 0 Rows affected.");
		}
		return result;
	}
	
}
