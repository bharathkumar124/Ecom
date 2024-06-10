package com.example.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Admin;




@Repository
public interface UserDao extends JpaRepository<Admin, Integer>{


List<Admin> findByEmailId(String emailId);
List<Admin> findByFirstNameAndLastName(String firstname, String lastName);

List<Admin> findByPhoneNo(String phoneNo);
}
