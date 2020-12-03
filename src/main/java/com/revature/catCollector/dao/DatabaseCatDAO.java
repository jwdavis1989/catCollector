package com.revature.catCollector.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.catCollector.model.Cat;
import com.revature.catCollector.util.JDBCUtility;

public class DatabaseCatDAO 
{

	public ArrayList<Cat> getAllCats() 
	{
		
		String sqlQuery = 
				"SELECT * FROM Cats";
		
		ArrayList<Cat> Cats = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			Statement stmt = connection.createStatement(); // Simple Statement, not to be confused Prepared Statement
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			//Fill in each arrayList Cat's UID, name, ownerName, color, breed, imageURL
			while (rs.next()) 
			{
				int UID = rs.getInt(1);
				String name = rs.getString(2);
				String ownerName = rs.getString(3);
				String color = rs.getString(4);
				String breed = rs.getString(5);
				
				Cat cat = new Cat(UID, name, ownerName, color, breed);
				
				Cats.add(cat);
			}
			
			return Cats;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Cats;
	}
	
	public Cat getCatByUID(int uid) 
	{
			
		Cat returnCat = new Cat();
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			
			//Create SQL query
			String sqlQuery = 
					"SELECT * " +
					"FROM Cats " + 
					"WHERE UID = ?";
			
			//Create Prepared Statement to stop SQL Injection
			PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
			
			//Fill in SQL Statement's values
			prepdStatement.setInt(1, uid);
			
			ResultSet rs = prepdStatement.executeQuery();
			
			//Fill in the return Cat's UID, name, ownerName, color, breed, imageURL
			if (rs.next()) 
			{
				returnCat = new Cat(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5));
			}
			else
			{
				throw new SQLException("Cat not found with this UID");
			}
			
			return returnCat;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return returnCat;
	}
	
	public ArrayList<Cat> getAllCatsByOwner(String targetOwnerName) 
	{
		
		
		//Allocate memory for the ArrayList that contains the returned cats
		ArrayList<Cat> Cats = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			
			//Create SQL query
			String sqlQuery = 
					"SELECT * " +
					"FROM Cats " + 
					"WHERE ownerName = ?";
			
			//Create Prepared Statement to stop SQL Injection
			PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
			
			//Fill in SQL Statement's values
			prepdStatement.setString(1, targetOwnerName);
			
			ResultSet rs = prepdStatement.executeQuery();
			
			//Fill in each arrayList Cat's UID, name, ownerName, color, breed, imageURL
			while (rs.next()) 
			{
				int UID = rs.getInt(1);
				String name = rs.getString(2);
				String ownerName = rs.getString(3);
				String color = rs.getString(4);
				String breed = rs.getString(5);
				
				Cat cat = new Cat(UID, name, ownerName, color, breed);
				
				Cats.add(cat);
			}
			
			return Cats;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Cats;
	}
	
	/**
	 * Inserts a Cat into the database. Uses JDBC to open a connection to the DB and then creates an
	 * SQL query in the form of:
	 * "INSERT INTO Cat "
					+ "(name, ownerName, color, breed, imageURL) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)"
					
		And then executes it using a PreparedStatement. A prepared statement is used in order to 
		sanitize for SQL injection
	 * @param name, the name of the Cat.
	 * @param ownerName, name of the Owner of the Cat.
	 * @param color, the color of the Cat. Dynamically sets the imageURL.
	 * @param breed, the breed of the Cat.
	 * @return Cat or null
	 */
	public Cat addNewCat(String name, String ownerName, String color, String breed) 
	{
		
		try (Connection connection = JDBCUtility.getConnection()) 
		{
			connection.setAutoCommit(false);
			String sqlQuery = 
					"INSERT INTO Cats "
					+ "(name, ownerName, color, breed, imageURL) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)";
			
			PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			
			prepdStatement.setString(1, name);
			prepdStatement.setString(2, ownerName);
			prepdStatement.setString(3, color);
			prepdStatement.setString(4, breed);
			prepdStatement.setString(5, "./resources/images/" + color);
			
			if (prepdStatement.executeUpdate() != 1) 
			{
				throw new SQLException("Inserting Cat failed, no rows were affected");
			}
			
			int autoId = 0;
			ResultSet generatedKeys = prepdStatement.getGeneratedKeys();
			if (generatedKeys.next()) 
			{
				autoId = generatedKeys.getInt(1);
			} 
			else 
			{
				throw new SQLException("Inserting Cat failed, no ID generated");
			}
			
			connection.commit();
			
			return new Cat(autoId, name, ownerName, color, breed);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
		

	public boolean updateCatByUID(int targetUID, String newName, String newOwnerName, String newColor, String newBreed) 
	{
		
		//Cat object created to test update's success
		Cat targetCat = getCatByUID(targetUID);
		
		//Stores whether the update was a success or not, defaults to not successful, as in all things in life
		boolean returnSuccess = false;
		
		if (targetCat != null) {
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				connection.setAutoCommit(false);
				String sqlQuery = 
						"UPDATE Cats " 			+ 
					    "SET name = ?, " 		+
							"ownerName = ?, " 	+
							"color = ?, " 		+
							"breed = ?, " 		+
							"imageURL = ? "		+
						"WHERE UID = ?";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
				
				prepdStatement.setString(1, newName);
				prepdStatement.setString(2, newOwnerName);
				prepdStatement.setString(3, newColor);
				prepdStatement.setString(4, newBreed);
				
				//Concatenate relative URL to Color as Color name = the individual Image's name
				prepdStatement.setString(5, "/resources/images/" + newColor);
				prepdStatement.setInt(6, targetUID);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Updating Cat failed, no rows were affected");
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
	


	public boolean removeCatByUID(int targetUID) 
	{
		
		//Cat object created to test update's success
		Cat targetCat = getCatByUID(targetUID);
		
		//Stores whether the deletion was a success or not, defaults to not successful, as in all things in life
		boolean returnSuccess = false;
		
		if (targetCat != null) {
			try (Connection connection = JDBCUtility.getConnection()) 
			{
				connection.setAutoCommit(false);
				String sqlQuery = 
						"DELETE FROM Cats " + 
						"WHERE UID = ?";
				
				PreparedStatement prepdStatement = connection.prepareStatement(sqlQuery);
			
				prepdStatement.setInt(1, targetUID);
				
				if (prepdStatement.executeUpdate() != 1) 
				{
					throw new SQLException("Removal of Cat failed, no rows were affected");
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

