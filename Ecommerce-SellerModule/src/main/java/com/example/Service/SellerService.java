package com.example.Service;

import java.util.List;

import com.example.entity.Seller;





public interface SellerService {
	Seller addSeller(Seller u);
	//List<Seller> fetchall(String role);
	Seller update(Seller user);
	boolean deletebyid(int id);
	List<Seller> findbyemailid(String email);
	
	List<Seller>login(String role,String email,String password);
	
	Seller findbyid(int id);
	
List<Seller>findbyphoneNo(String phoneno);

List<Seller> findByStatusAndRole(String status, String role);
Seller findByIdAndStatus(int id, String status);

}
