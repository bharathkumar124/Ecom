package com.example.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SellerDao;
import com.example.entity.Seller;



@Service
public class SellerServiceimpl implements SellerService {
	@Autowired
	SellerDao dao;

	@Override
	public Seller addSeller(Seller u) {
		// TODO Auto-generated method stub
		return dao.save(u);
	}

	@Override
	public Seller update(Seller c) {
		// TODO Auto-generated method stub
		return dao.save(c);
	}

	@Override
	public List<Seller> fetchall(String role) {

		return dao.findByRole(role);
	}

	@Override
	public boolean deletebyid(int id) {
		// TODO Auto-generated method stub
		if (dao.existsById(id)) {
			dao.deleteById(id);
			return true;
		} else
			return false;
	}

	@Override
	public List<Seller> findbyemailid(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmailId(email);
	}

	@Override
	public List<Seller> login(String role, String email, String password) {
		List<Seller> log = dao.findByRoleAndEmailIdAndPassword(role, email, password);
		if(log.isEmpty()) {
			return Collections.emptyList();
		}else
		return log;
	}

	@Override
	public Seller findbyid(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public List<Seller> findbyphoneNo(String phoneno) {
		// TODO Auto-generated method stub
		return dao.findByPhoneNo(phoneno);
	}

	

}
