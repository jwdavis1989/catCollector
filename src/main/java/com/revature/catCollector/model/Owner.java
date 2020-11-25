package com.revature.catCollector.model;

public class Owner 
{
	
	private String username;	//Unique
	private String password;
	private Boolean admin;
	private String sessionData;
	
	
	
	public Owner() 
	{
		super();
		this.sessionData = null;
	}

	public Owner(String username, String password, Boolean admin) 
	{
		super();
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.sessionData = null;
	}

	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public Boolean getAdmin() 
	{
		return admin;
	}
	
	public void setAdmin(Boolean admin) 
	{
		this.admin = admin;
	}

	public String getSessionData() 
	{
		return sessionData;
	}

	public void setSessionData(String sessionData) 
	{
		this.sessionData = sessionData;
	}

	@Override
	public String toString() 
	{
		return "Owner [username=" + username + ", password=" + password + ", admin=" + admin + ", sessionData="
				+ sessionData + "]";
	}

	
	
	
}
