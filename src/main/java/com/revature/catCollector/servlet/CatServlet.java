package com.revature.catCollector.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.catCollector.dao.DatabaseCatDAO;
import com.revature.catCollector.model.Cat;
import com.revature.catCollector.service.CatService;

public class CatServlet extends HttpServlet
{
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private CatService catService = new CatService();
	
	public CatServlet() 
	{
		super();
	}
	
	public CatServlet(ObjectMapper objectMapper, CatService catService) 
	{
		this.objectMapper = objectMapper;
		this.catService = catService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		try 
		{
			String jsonString = objectMapper.writeValueAsString(catService.getAllCats());
			resp.getWriter().append(jsonString);
			
			resp.setContentType("application/json"); // This corresponds to MIME type standards
			resp.setStatus(200);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			resp.setStatus(500);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		BufferedReader reader = req.getReader();
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) 
		{
			sb.append(line);
		}
		
		String jsonString = sb.toString();
		
		try 
		{
			// Jackson Databind parsing the jsonString and creating an InsertCatTemplate object, with that data
			Cat catData = objectMapper.readValue(jsonString, Cat.class);
			
			Cat catCollector = catService.addNewCat(catData.getName(), catData.getOwnerName(), catData.getColor(), catData.getBreed());
			
			String insertedCatJSON = objectMapper.writeValueAsString(catCollector);
			resp.getWriter().append(insertedCatJSON);
			
			resp.setContentType("application/json");
			resp.setStatus(201);
		} 
		catch (JsonProcessingException | SQLException e) 
		{
			resp.setStatus(400);
		}
	}	
}
