package com.example.Service;

import java.util.List;

import com.example.entity.Product;
import com.example.entity.Seller;


public interface ProductService {

	Product addpro(Product p);
	
	Product update(Product p);
	
	boolean delete(int id);
	
	List<Product> fetchall();
	
	Product findbyid(int id);
	
	List<Product> findBySeller(Seller seller);
	
	void deleteBySeller(Seller seller);
	
	Product findByIdAndStatus(int id, String status);
	
	List<Product> findByStatus(String status);
}
