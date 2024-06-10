package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.UserDao;
import com.example.entity.Customer;



@Service
public class UserServiceimpl implements UserService{
	@Autowired
	UserDao dao;
	@Override
	public Customer add(Customer u) {
		// TODO Auto-generated method stub
		return dao.save(u) ;
	}
	@Override
	public List<Customer> findbyemail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmailId(email);
	}
	@Override
	public List<Customer> findbyfirstnameAndlastname(String first, String last) {
		// TODO Auto-generated method stub
		return dao.findByFirstNameAndLastName(first, last);
	}
	@Override
	public List<Customer> findbyphoneNo(String phoneNo) {
		
		return dao.findByPhoneNo(phoneNo);
	}
	@Override
	public Customer findbyid(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}
	@Override
	public List<Customer> findByallcustomer(String role) {
		// TODO Auto-generated method stub
		return dao.findByRole(role);
	}
	

}
