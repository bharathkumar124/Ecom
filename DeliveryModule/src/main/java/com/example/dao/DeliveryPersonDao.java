package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.DeliveryPerson;
import com.example.entity.Seller;

@Repository
public interface DeliveryPersonDao extends JpaRepository<DeliveryPerson, Integer> {
    
	List<DeliveryPerson> findByseller_id(int id);
	
	List<DeliveryPerson> findBySellerAndRoleAndStatus(Seller seller, String role, String status);
	DeliveryPerson findByIdAndStatus(int id, String status);
}
