package com.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.DeliveryPersonDao;
import com.example.entity.DeliveryPerson;
import com.example.entity.Seller;




@Service
public class DeliveryServiceimpl implements DeliveryService {
	
	@Autowired
	DeliveryPersonDao dao;

	@Override
	public DeliveryPerson adddeliverper(DeliveryPerson u) {
			return dao.save(u);
	}

	@Override
	public DeliveryPerson findbyid(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public List<DeliveryPerson> findbysellerroleAndStatus(Seller seller, String role, String status) {
		// TODO Auto-generated method stub
		return dao.findBySellerAndRoleAndStatus(seller, role, status);
	}

	@Override
	public boolean delete(int id) {
	if(!dao.existsById(id)) {
		return false;
	}else
		return true;
	}

	@Override
	public List<DeliveryPerson> findall() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public DeliveryPerson findByIdAndStatus(int id, String status) {
		// TODO Auto-generated method stub
		return dao.findByIdAndStatus(id, status);
	}

	@Override
	public DeliveryPerson update(DeliveryPerson dp) {
		// TODO Auto-generated method stub
		return dao.save(dp);
	}

	

}
