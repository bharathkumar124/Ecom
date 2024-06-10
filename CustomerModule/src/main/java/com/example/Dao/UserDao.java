package com.example.Dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Customer;




@Repository
public interface UserDao extends JpaRepository<Customer, Integer>{


List<Customer> findByEmailId(String emailId);
List<Customer> findByFirstNameAndLastName(String firstname, String lastName);

List<Customer> findByPhoneNo(String phoneNo);

List<Customer> findByRole(String role);
}
