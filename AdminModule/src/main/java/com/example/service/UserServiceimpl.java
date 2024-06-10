package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.entity.Admin;



@Service
public class UserServiceimpl implements UserService{
	@Autowired
	UserDao dao;
	@Override
	public Admin add(Admin u) {
		// TODO Auto-generated method stub
		return dao.save(u) ;
	}
	@Override
	public List<Admin> findbyemail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmailId(email);
	}
	@Override
	public List<Admin> findbyfirstnameAndlastname(String first, String last) {
		// TODO Auto-generated method stub
		return dao.findByFirstNameAndLastName(first, last);
	}
	@Override
	public List<Admin> findbyphoneNo(String phoneNo) {
		
		return dao.findByPhoneNo(phoneNo);
	}
	

}
