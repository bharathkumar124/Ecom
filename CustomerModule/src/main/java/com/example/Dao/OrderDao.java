package com.example.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Customer;
import com.example.entity.Orders;



@Repository
public interface OrderDao extends JpaRepository<Orders, Integer>{


	List<Orders> findByOrderId(String orderId);
	
	void deleteByOrderId(String orderId);
	
	List<Orders> findByUser(Customer user);
	List<Orders> findByDeliveryPerson(Customer deliveryPerson);
	
	
}
