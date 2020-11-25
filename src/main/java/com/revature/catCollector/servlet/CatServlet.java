package com.revature.catCollector.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.catCollector.model.Cat;
import com.revature.catCollector.service.CatService;



public class CatServlet extends HttpServlet
{	
	//static Logger log = LogManager.getLogger(CatServlet.class);
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

	//Read - CRUD
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		String jsonString;
		try 
		{
			String owner = req.getParameter("ownerName");
			String uid = req.getParameter("UID");
			
			if (owner != null)
			{
				jsonString = objectMapper.writeValueAsString(catService.getAllCatsByOwner(owner));
			}
			else if (uid != null)
			{
				jsonString = objectMapper.writeValueAsString(catService.getCatByUID(Integer.parseInt(uid)));
			}
			else
			{
				jsonString = objectMapper.writeValueAsString(catService.getAllCats());
			}

			//Added generated JSON to the Response, so it can be used by the front end
			resp.getWriter().append(jsonString);
			
			resp.setContentType("application/json"); // This corresponds to MIME type standards
			
			//Successful Code Return
			resp.setStatus(200);
	
		}
		catch (SQLException | NumberFormatException e) 
		{
			e.printStackTrace();
			
			resp.setStatus(500);
		}
		
	}
	
	//Create - CRUD
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
			
			//Success
			resp.setStatus(201);
		} 
//		catch (JsonProcessingException | SQLException e)
		catch (Exception e)
		{
			//Bad Content
			resp.setStatus(400);
		}
	}	
	
	//Update - CRUD
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
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
			
			Boolean catUpdateSuccess = catService.updateCatByUID(catData.getUID(), catData.getName(), catData.getOwnerName(), catData.getColor(), catData.getBreed());
			
			if (catUpdateSuccess)
			{
				//Success - Should Never Return a Body
				resp.setStatus(200);
			}
			else
			{
				//Internal Server Error
				resp.setStatus(500);
			}
		} 
		//catch (JsonProcessingException | SQLException e) 
		catch (Exception e)
		{
			//Bad Request
			resp.setStatus(400);
			
		}
	}	
	
	//Delete - CRUD
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		//removeCatByUID
		try 
		{
			String uid = req.getParameter("UID");
			
			if (uid != null)
			{
				if (catService.removeCatByUID(Integer.parseInt(uid)))
				{
					//You done good Mr. President
					resp.setStatus(200);
				}
				else
				{
					//Internal Server Error
					resp.setStatus(500);
				}
			}
			else
			{
				//Bad Request
				resp.setStatus(400);
			}
	
		}
		catch (SQLException | NumberFormatException e) 
		{
			e.printStackTrace();

			//Internal Server Error
			resp.setStatus(500);
		}
	}
	
}
