package com.example.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CategoryDao;
import com.example.entity.Category;


@Service
public class CategoryServiceimpl implements CategoryService {
	@Autowired
	CategoryDao dao;

	@Override
	public Category add(Category c) {
		
		return dao.save(c);
	}

	

	@Override
	public boolean delete(int id) {
		if(!dao.existsById(id)) {
			return false;
			
		}else
		dao.deleteById(id);
		return true;
	}

	@Override
	public Category update(Category c) {
		// TODO Auto-generated method stub
		return dao.save(c);
	}


	@Override
	public List<Category> getCategoriesByStatusIn(List<String> status) {
	    // Make sure dao is not null
	    if (dao == null) {
	        return Collections.emptyList(); // Return an empty list if DAO is not available
	    }
	    return this.dao.findByStatusIn(status);
	}
}
