package com.revature.catCollector.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.catCollector.model.Owner;
import com.revature.catCollector.util.JDBCUtility;

public class DatabaseOwnerDAO {

	public Owner findOwnerByUserName(String name) {
		
		try (Connection connection = JDBCUtility.getConnection()) {
			String sqlQuery = 
					"SELECT username, password, admin " +
					"FROM Owners " +
					"WHERE username = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				Boolean admin = rs.getBoolean(3);
				return new Owner(username, password, admin);
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
