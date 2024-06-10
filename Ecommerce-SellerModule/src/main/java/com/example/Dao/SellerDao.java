package com.example.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Seller;




@Repository
public interface SellerDao extends JpaRepository<Seller, Integer>{
	
	List<Seller> findByRole(String role);
List<Seller> findByEmailId(String emailId);

List<Seller> findByRoleAndEmailIdAndPassword(String role, String emailId, String password);

List<Seller> findByPhoneNo(String phoneNo);
List<Seller> findByStatusAndRole(String status, String role);

Seller findByIdAndStatus(int id, String status);
}
