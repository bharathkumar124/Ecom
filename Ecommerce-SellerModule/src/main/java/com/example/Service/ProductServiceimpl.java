package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.ProductDao;
import com.example.entity.Product;
import com.example.entity.Seller;


@Service
public class ProductServiceimpl implements ProductService{
	
	@Autowired
	ProductDao dao;

	@Override
	public Product addpro(Product p) {
		// TODO Auto-generated method stub
		return dao.save(p);
	}

	@Override
	public Product update(Product p) {
		// TODO Auto-generated method stub
		return dao.save(p);
	}

	@Override
	public boolean delete(int id) {
		if(!dao.existsById(id)) {
			
			return false;
		}
		dao.deleteById(id);
		return true;
	}

	@Override
	public List<Product> fetchall() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Product findbyid(int id) {
		Product p = dao.findById(id).get();
		return p;
	}

	@Override
	public List<Product> findBySeller(Seller seller) {
		// TODO Auto-generated method stub
		return dao.findBySeller(seller);
	}

	
	@Override
	public void deleteBySeller(Seller seller) {
		dao.deleteBySeller(seller);
		
	}

	@Override
	public Product findByIdAndStatus(int id, String status) {
		// TODO Auto-generated method stub
		return dao.findByIdAndStatus(id, status);
	}

	@Override
	public List<Product> findByStatus(String status) {
		// TODO Auto-generated method stub
		return dao.findByStatus(status);
	}

	
}
