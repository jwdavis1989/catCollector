package com.revature.catCollector.servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.catCollector.model.Cat;
import com.revature.catCollector.service.CatService;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class CatServletTest extends TestCase {
	private static CatServlet catServlet;
	private static CatService mockCatService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private static Cat cat1;
	private static Cat cat2;
	
	private static ArrayList<Cat> catsArrayList; 
			
	//Init mock before each run
	@BeforeClass 
	public static void init() throws SQLException
	{
		//Create new mock service, at mock speed!
		mockCatService = mock(CatService.class);
		catServlet = new CatServlet(objectMapper, mockCatService);
		cat1 = new Cat(99, "Fluffy", "Dan", "Gray","Unknown Breed");
		cat2 = new Cat(100, "Garfield", "Tyler", "Orange", "Fat");
		
		catsArrayList = new ArrayList<Cat>();
		catsArrayList.add(cat1);
		catsArrayList.add(cat2);
		
		//Mock Retrieving All Cats	/cat Path
		when(mockCatService.getAllCats()).thenReturn(catsArrayList);
	}
	
	@Test
	public void doGetAllCatTest() throws ServletException, IOException
	{
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        String jsonString = objectMapper.writeValueAsString(catsArrayList);
        
        //Added generated JSON to the Response, so it can be used by the front end
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
		
        //Attempt a doGet on the CatServlet
        catServlet.doGet(request, response); 
        assertTrue(stringWriter.toString().equals(jsonString));
        
        
	}
	
}
