package com.example.Service;

import java.util.List;

import com.example.entity.Customer;



public interface UserService {
	Customer add(Customer u);

List<Customer>findbyemail(String email);

List<Customer> findbyfirstnameAndlastname(String first, String last);

List<Customer> findbyphoneNo(String phoneNo);

Customer findbyid(int id);
List<Customer> findByallcustomer(String role);
}
