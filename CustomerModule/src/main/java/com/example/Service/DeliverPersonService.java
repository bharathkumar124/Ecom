package com.example.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.DeliverPersonDao;
import com.example.entity.DeliveryPerson;

@Service
public class DeliverPersonService {

	@Autowired
	DeliverPersonDao dao;
	
	public Optional<DeliveryPerson> findbyid(int id) {
		return dao.findById(id);
	}
}
