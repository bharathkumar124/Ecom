package com.example.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.DeliveryPersonOrder;
import java.util.List;


@Repository
public interface DeliveryOrderdao extends JpaRepository<DeliveryPersonOrder, Integer>{

	List<DeliveryPersonOrder> findByDeliverpersonid(int deliverpersonid);
	
	DeliveryPersonOrder findByOrderid(String orderid);
}
