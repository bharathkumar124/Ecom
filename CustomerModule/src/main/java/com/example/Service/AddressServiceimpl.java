package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.AddressDao;
import com.example.entity.Address;

@Service
public class AddressServiceimpl implements AddressService{

	@Autowired
	AddressDao dao;
	@Override
	public Address save(Address add) {
		// TODO Auto-generated method stub
		return dao.save(add);
	}

}
