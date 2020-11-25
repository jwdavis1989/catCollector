package com.revature.catCollector.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.catCollector.model.Cat;
import com.revature.catCollector.model.Owner;
import com.revature.catCollector.util.JDBCUtility;

public class DatabaseOwnerDAO 
{
	public ArrayList<Owner> getAllOwners() 
	{
		
		String sqlQuery = 
				"SELECT * FROM Owners";
		
		ArrayList<Owner> Owners = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			Statement stmt = connection.createStatement(); // Simple Statement, not to be confused Prepared Statement
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			//Fill in each arrayList Cat's UID, name, ownerName, color, breed, imageURL
			while (rs.next()) 
			{
				String username = rs.getString(1);
				String password = rs.getString(2);
				Boolean isAdmin = rs.getBoolean(3);
				String sessionData = rs.getString(4);
				
				Owner owner = new Owner(username, password, isAdmin);
				
				Owners.add(owner);
			}
			
			return Owners;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Owners;
	}
	public Owner getOwnerByUsername(String name) 
	{
		
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			String sqlQuery = 
					"SELECT * " +
					"FROM Owners " +
					"WHERE username = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) 
			{
				String username = rs.getString(1);
				String password = rs.getString(2);
				Boolean admin = rs.getBoolean(3);
				return new Owner(username, password, admin);
			} 
			else 
			{
				return null;
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Owner addNewOwner(String username, String password, Boolean isAdmin) 
	{
			
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				String sqlQuery = 
						"INSERT INTO Owners "
						+ "(username, password, isAdmin, sessionData)"
						+ "VALUES "
						+ "(?, ?, ?, ?)";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
				
				prepdStatement.setString(1, username);
				prepdStatement.setString(2, password);
				prepdStatement.setBoolean(3, isAdmin);
				prepdStatement.setString(4, null);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Inserting User failed, no rows were affected");
				}
				
				connection.commit();
				
				return new Owner (username, password, isAdmin);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return null;
	}
	
	public boolean removeOwnerByUsername(String targetName) 
	{
		
		//Owner object created to test update's success
		Owner targetOwner = getOwnerByUsername(targetName);
		
		//Stores whether the deletion was a success or not, defaults to not successful, as in all things in life
		boolean returnSuccess = false;
		
		if (targetOwner != null) {
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				String sqlQuery = 
						"DELETE FROM Owners " + 
						"WHERE username = ?";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
			
				prepdStatement.setString(1, targetName);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Removal of Owner failed, no rows were affected");
				}
				
				//Commit the changes to the database
				connection.commit();
				
				//Everything worked
				returnSuccess = true;
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
	return returnSuccess;
	}

	public boolean updateOwnerByUsername(String targetUsername, String newPassword, Boolean newIsAdmin, String newSessionData) 
	{
		
		//Cat object created to test update's success
		Owner targetOwner = getOwnerByUsername(targetUsername);
		
		//Stores whether the update was a success or not, defaults to not successful, as in all things in life
		boolean returnSuccess = false;
		
		if (targetOwner != null) {
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				connection.setAutoCommit(false);
				String sqlQuery = 
						"UPDATE Owners " 		+ 
					    "SET password = ?, " 	+
					    "isAdmin = ? "			+
					    "sessionData = ? "		+
						"WHERE username = ?";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
				
				prepdStatement.setString(1, newPassword);
				prepdStatement.setBoolean(2, newIsAdmin);
				prepdStatement.setString(3, newSessionData);
				prepdStatement.setString(4, targetUsername);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Updating Owner failed, no rows were affected");
				}
				
				//Commit the changes to the database
				connection.commit();
				
				//Everything worked
				returnSuccess = true;
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return returnSuccess;
	}
}
