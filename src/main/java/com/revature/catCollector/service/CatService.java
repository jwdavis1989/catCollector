package com.revature.catCollector.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.catCollector.dao.DatabaseCatDAO;
import com.revature.catCollector.dao.DatabaseOwnerDAO;
import com.revature.catCollector.model.Cat;
import com.revature.catCollector.model.Owner;
import com.revature.catCollector.template.InsertCatTemplate;

public class CatService {
	
	private DatabaseCatDAO catDAO;
	private DatabaseOwnerDAO ownerDAO;
	
	public CatService() {
		this.catDAO = new DatabaseCatDAO();
	}
	
	// Generally this constructor is for doing unit testing
	// You should basically mock the DatabaseCatDAO object, and then pass it into the CatService object
	// In order to simulate an actual database
	// So when you're unit testing, generally you should not actually be using the actual database, and instead you should
	// mock returns from the DAO layer to the service layer
	
	// Look into using Mockito
	// There should definitely be a lot of articles about mocking DAOs
	public CatService(DatabaseCatDAO catDAO, DatabaseOwnerDAO ownerDAO) {
		this.catDAO = catDAO;
		this.ownerDAO = ownerDAO;
	}
	
	public ArrayList<Cat> getAllCats() {
		// If you have additional you want to add later, you can easily do it by something like this
		return catDAO.getAllCats();
	}
	
//	public void showCats(ArrayList<Cat> catCollectors) {
//		for (Cat catCollector : catCollectors) {
//			System.out.println(catCollector);
//		}
//	}
//	
//	public int search(ArrayList<Cat> catCollectors, String name) {
//		int index = -1;
//		for(Cat catCollector:catCollectors) {
//			index++;
//			if(catCollector.getName().equals(name)) {
//				return index;
//			}
//		}
//		return -1;
//		
//	}
//	
//	public Cat setCat(ArrayList<Cat> catCollectors, String name, String owner){
//		// Set owner of Cat
//				int index = this.search(catCollectors, name);
//				if(index == -1) {
//					System.out.println("Cat not found Exception"); // we could make our own exception
//					return null;
//				}else {
//
//					catCollectors.get(index).setowner(owner);
//					return catCollectors.get(index);
//				}
//	}
}
