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
import com.revature.catCollector.model.Owner;
import com.revature.catCollector.service.OwnerService;



public class OwnerServlet extends HttpServlet
{	
	//static Logger log = LogManager.getLogger(OwnerServlet.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	private OwnerService ownerService = new OwnerService();
	
	public OwnerServlet() 
	{
		super();
	}
	
	public OwnerServlet(ObjectMapper objectMapper, OwnerService ownerService) 
	{
		this.objectMapper = objectMapper;
		this.ownerService = ownerService;
	}

	//Read - CRUD
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		String jsonString = "";
		try 
		{
			String username = req.getParameter("username");
			
			if (username != null)
			{
				jsonString = objectMapper.writeValueAsString(ownerService.getOwnerByUsername(username));
			}
			else
			{
				jsonString = objectMapper.writeValueAsString(ownerService.getAllOwners());
			}

			//Added generated JSON to the Response, so it can be used by the front end
			resp.getWriter().append(jsonString);
			
			resp.setContentType("appliownerion/json"); // This corresponds to MIME type standards
			
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
			// Jackson Databind parsing the jsonString and creating an InsertOwnerTemplate object, with that data
			Owner ownerData = objectMapper.readValue(jsonString, Owner.class);
			
			Owner newOwner = ownerService.addNewOwner(ownerData.getUsername(), ownerData.getPassword(), ownerData.getAdmin());
			
			String insertedOwnerJSON = objectMapper.writeValueAsString(newOwner);
			resp.getWriter().append(insertedOwnerJSON);
			
			resp.setContentType("appliownerion/json");
			
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
			Owner ownerData = objectMapper.readValue(jsonString, Owner.class);
			
			Boolean ownerUpdateSuccess = ownerService.updateOwnerByUsername(ownerData.getUsername(), ownerData.getPassword(), ownerData.getAdmin(), ownerData.getSessionData());
			
			if (ownerUpdateSuccess)
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
		//removeOwnerByUID
		try 
		{
			String targetUsername = req.getParameter("username");
			
			if (targetUsername != null)
			{
				if (ownerService.removeOwnerByUsername(targetUsername))
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
