package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.DeliveryOrderdao;
import com.example.entity.DeliveryPersonOrder;

@Service
public class DeliveryPersonorderService {

	
	@Autowired
	DeliveryOrderdao dao;
	
	public DeliveryPersonOrder adddorder(DeliveryPersonOrder order) {
		return dao.save(order);
	}
	
	public List<DeliveryPersonOrder> findByDeliverpersonid(int deliverpersonid){
		return dao.findByDeliverpersonid(deliverpersonid);
	}
	
	public DeliveryPersonOrder findByOrderid(String orderid) {
		return dao.findByOrderid(orderid);
	}
	
	public DeliveryPersonOrder update(DeliveryPersonOrder order) {
		return dao.save(order);
	}
	
}
