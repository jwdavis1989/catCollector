package com.revature.catCollector.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.catCollector.model.Cat;
import com.revature.catCollector.util.JDBCUtility;

public class DatabaseCatDAO {

	public ArrayList<Cat> getAllCats() {
		
		String sqlQuery = 
				"SELECT UID, name, ownerName, color, breed, imageURL" +
				"FROM Cats";
		
		ArrayList<Cat> Cats = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) {
			Statement stmt = connection.createStatement(); // Simple Statement, not to be confused Prepared Statement
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			//Fill in each arrayList Cat's UID, name, ownerName, color, breed, imageURL
			while (rs.next()) {
				int UID = rs.getInt(1);
				String name = rs.getString(2);
				String ownerName = rs.getString(3);
				String color = rs.getString(4);
				String breed = rs.getString(5);
				String imageURL = rs.getString(6);
				
				Cat cat = new Cat(UID, name, ownerName, color, breed, imageURL);
				
				Cats.add(cat);
			}
			
			return Cats;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Cats;
	}
	
	public ArrayList<Cat> getAllCatsYouOwn(String yourOwnerName) {
		
		String sqlQuery = 
				"SELECT UID, name, ownerName, color, breed, imageURL" +
				"FROM Cats" + 
				"WHERE ownerName = ?";
		
		PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, ownerName);
		
		//Allocate memory for the ArrayList that contains the returned cats
		ArrayList<Cat> Cats = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			String sqlQuery = 
					"INSERT INTO Cats "
					+ "(name, ownerName, color, breed, imageURL)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, name);
			pstmt.setString(2, ownerName);
			pstmt.setString(3, color);
			pstmt.setString(4, breed);
			pstmt.setString(5, imageURL);
			
			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting Cat failed, no rows were affected");
			}
			
			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting Cat failed, no ID generated");
			}
			
			connection.commit();
			
			return new Cat(autoId, name, ownerName, color, breed, imageURL);
		} catch (SQLException e) {
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
	 * @param color, the color of the Cat.
	 * @param breed, the breed of the Cat.
	 * @param imageURL, the location of the image on the server's disk.
	 * @return Cat or null
	 */
	public Cat insertCat(String name, String ownerName, String color, String breed, String imageURL) {
		
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false); // Committing it goes into the whole idea of transactions. By default, Postgres is set to
			// Automatically commit any changes you make to the database. Sometimes this is not ideal, especially if you want to 
			// make sure all operations are successful before persisting changes (committing). 
			// Bank transfers might be a good example. For example you will have to withdraw money from one account and deposit to another
			// If you have auto-commit set to true, if you withdraw from the first account, and then somehow your database loses power,
			// the withdraw from the first account would already have been committed
			
			// This is not ideal, because we would only want to commit once the withdraw from one account and the deposit to another account
			// Has successfully been completed
			
			// ACID properties of transactions, Atomicity, Consistency, Isolation, Durability
			String sqlQuery = 
					"INSERT INTO Cats "
					+ "(name, ownerName, color, breed, imageURL)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, name);
			pstmt.setString(2, ownerName);
			pstmt.setString(3, color);
			pstmt.setString(4, breed);
			pstmt.setString(5, imageURL);
			
			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting Cat failed, no rows were affected");
			}
			
			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting Cat failed, no ID generated");
			}
			
			connection.commit();
			
			return new Cat(autoId, name, ownerName, color, breed, imageURL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
		
}
