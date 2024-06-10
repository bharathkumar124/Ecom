package com.example.service;

import java.util.List;

import com.example.entity.DeliveryPerson;
import com.example.entity.Seller;





public interface DeliveryService {
	
	DeliveryPerson adddeliverper(DeliveryPerson u);
	
	DeliveryPerson findbyid(int id);
	
	List<DeliveryPerson> findbysellerroleAndStatus( Seller seller,String role,String status);
	 boolean delete(int id);
	 
	 List<DeliveryPerson>findall();
	 
		DeliveryPerson findByIdAndStatus(int id, String status);
		
		DeliveryPerson update(DeliveryPerson dp);
	// List<User> findbyrole(String role);
	/*
	 * 
	 * 
	 * User update(User u);
	 * 
	 * boolean delete(int id);
	 * 
	 *
	 * 
	 
	 */

}
