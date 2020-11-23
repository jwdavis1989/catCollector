package com.revature.catCollector.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.catcollector.dao.DatabaseCatCollectorDAO;
import com.revature.catCollector.exception.RoleNotFoundException;
import com.revature.catCollector.model.CatCollector;
import com.revature.catCollector.service.CatService;
import com.revature.catCollector.template.InsertCatCollectorTemplate;

public class CatCollectorServlet extends HttpServlet{
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private CatService catCollectorService = new CatService();
	
	public CatCollectorServlet() {
		super();
	}
	
	public CatCollectorServlet(ObjectMapper objectMapper, CatService catCollectorService) {
		this.objectMapper = objectMapper;
		this.catCollectorService = catCollectorService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String jsonString = objectMapper.writeValueAsString(catCollectorService.getAllCatCollectors());
		resp.getWriter().append(jsonString);
		
		resp.setContentType("application/json"); // This corresponds to MIME type standards
		resp.setStatus(200);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		String jsonString = sb.toString();
		
		try {
			// Jackson Databind parsing the jsonString and creating an InsertCatCollectorTemplate object, with that data
			InsertCatCollectorTemplate catCollectorData = objectMapper.readValue(jsonString, InsertCatCollectorTemplate.class);
			
			CatCollector catCollector = catCollectorService.insertCatCollectors(catCollectorData);
			
			String insertedCatCollectorJSON = objectMapper.writeValueAsString(catCollector);
			resp.getWriter().append(insertedCatCollectorJSON);
			
			resp.setContentType("application/json");
			resp.setStatus(201);
		} catch (JsonProcessingException | RoleNotFoundException e) {
			resp.setStatus(400);
		}
		
	}
	
}
