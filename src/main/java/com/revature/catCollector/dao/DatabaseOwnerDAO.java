package com.revature.catCollector.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.catCollector.model.Owner;
import com.revature.catCollector.util.JDBCUtility;

public class DatabaseOwnerDAO 
{
	public Owner getOwnerByUsername(String name) 
	{
		
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			String sqlQuery = 
					"SELECT username, password, isAdmin " +
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
						+ "(username, password, isAdmin)"
						+ "VALUES "
						+ "(?, ?, ?)";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
				
				prepdStatement.setString(1, username);
				prepdStatement.setString(2, password);
				prepdStatement.setBoolean(3, isAdmin);
				
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
	
	public boolean removeOwnerByName(String targetName) 
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

	public boolean changeOwnerAdminRightsByUsername(String targetName, Boolean newAdminState) 
	{
		
		//Owner object created to test update's success
		Owner targetOwner = getOwnerByUsername(targetName);
		
		//Stores whether the update was a success or not, defaults to not successful, as in all things in life
		boolean returnSuccess = false;
		
		if (targetOwner != null) {
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				String sqlQuery = 
						"UPDATE Owners " 		+ 
					    "SET isAdmin = ?, " 		+
						"WHERE username = ?";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
				
				prepdStatement.setBoolean(1, newAdminState);
				prepdStatement.setString(2, targetName);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Updating Owner Admin Rights failed, no rows were affected");
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

	public boolean changeOwnerPasswordByUsername(String targetName, String newPassword) 
	{
		
		//Owner object created to test update's success
		Owner targetOwner = getOwnerByUsername(targetName);
		
		//Stores whether the update was a success or not, defaults to not successful, as in all things in life
		boolean returnSuccess = false;
		
		if (targetOwner != null) {
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				String sqlQuery = 
						"UPDATE Owners " 		+ 
					    "SET password = ?, " 		+
						"WHERE username = ?";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
				
				prepdStatement.setString(1, newPassword);
				prepdStatement.setString(2, targetName);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Updating Owner Password failed, no rows were affected");
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
