package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.ProductDao;
import com.example.entity.Product;

@Service
public class ProductService {
	@Autowired
	ProductDao dao;

	
	public Product getproductbyid(int id) {
		return dao.findById(id).get();
		
	}
}
