package com.example.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Product;
import java.util.List;
import com.example.entity.Seller;



@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{

	List<Product> findBySeller(Seller seller);
	void deleteBySeller(Seller seller);
	
	Product findByIdAndStatus(int id, String status);
	
	List<Product> findByStatus(String status);
}
