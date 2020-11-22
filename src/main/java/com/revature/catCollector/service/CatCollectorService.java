package com.revature.catCollector.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.catCollector.dao.DatabaseCatDAO;
import com.revature.catCollector.dao.DatabaseOwnerDAO;
import com.revature.catCollector.model.Cat;
import com.revature.catCollector.model.Owner;
import com.revature.catCollector.template.InsertCatCollectorTemplate;

public class CatCollectorService {
	
	private DatabaseCatDAO catCollectorDAO;
	private DatabaseOwnerDAO ownerDAO;
	
	public CatCollectorService() {
		this.catCollectorDAO = new DatabaseCatDAO();
		this.ownerDAO = new DatabaseOwnerDAO();
	}
	
	// Generally this constructor is for doing unit testing
	// You should basically mock the DatabaseCatDAO object, and then pass it into the CatCollectorService object
	// In order to simulate an actual database
	// So when you're unit testing, generally you should not actually be using the actual database, and instead you should
	// mock returns from the DAO layer to the service layer
	
	// Look into using Mockito
	// There should definitely be a lot of articles about mocking DAOs
	public CatCollectorService(DatabaseCatDAO catCollectorDAO, DatabaseOwnerDAO ownerDAO) {
		this.catCollectorDAO = catCollectorDAO;
		this.ownerDAO = ownerDAO;
	}
	
	public ArrayList<CatCollector> getAllCatCollectors() {
		// If you have additional you want to add later, you can easily do it by something like this
		return catCollectorDAO.getAllCatCollectors();
	}
	
	public CatCollector insertCatCollectors(InsertCatCollectorTemplate catCollectorTemplate) throws ownerNotFoundException {
		// What we want to accomplish is taking the catCollector name, and owner name
		// and then calling the ownerDAO to get an actual owner corresponding to that owner name,
		// Once we do that, we will know what the owner id is, and then we can actually insert
		// the catCollector into the catCollector table (name, owner_id)
		Owner owner;
		owner = ownerDAO.findownerByName(catCollectorTemplate.getowner_name());
		
		if (owner == null) {
			throw new NotFoundException("The user attempted to insert a catCollector for a owner that does not exist");
		}
		
		return catCollectorDAO.insertCatCollector(catCollectorTemplate.getName(), owner);
	}
	
//	public void showCatCollectors(ArrayList<CatCollector> catCollectors) {
//		for (CatCollector catCollector : catCollectors) {
//			System.out.println(catCollector);
//		}
//	}
//	
//	public int search(ArrayList<CatCollector> catCollectors, String name) {
//		int index = -1;
//		for(CatCollector catCollector:catCollectors) {
//			index++;
//			if(catCollector.getName().equals(name)) {
//				return index;
//			}
//		}
//		return -1;
//		
//	}
//	
//	public CatCollector setCatCollector(ArrayList<CatCollector> catCollectors, String name, String owner){
//		// Set owner of CatCollector
//				int index = this.search(catCollectors, name);
//				if(index == -1) {
//					System.out.println("CatCollector not found Exception"); // we could make our own exception
//					return null;
//				}else {
//
//					catCollectors.get(index).setowner(owner);
//					return catCollectors.get(index);
//				}
//	}
}
