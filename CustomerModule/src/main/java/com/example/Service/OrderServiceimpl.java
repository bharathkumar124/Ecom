package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.OrderDao;
import com.example.entity.Customer;
import com.example.entity.Orders;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceimpl  implements OrderService{
	
	@Autowired
	OrderDao dao;

	@Override
	public List<Orders> addOrder(List<Orders> orders) {
		// TODO Auto-generated method stub
		return dao.saveAll(orders);
	}

	@Override
	public Orders updateorder(Orders order) {
		// TODO Auto-generated method stub
		return dao.save(order);
	}

	@Override
	public List<Orders> fidbyid(String orderid) {
		
		return dao.findByOrderId(orderid);
	}

	@Override
	public List<Orders> fetchall() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	  @Transactional
	public boolean delete(String orderid) {
		if(!dao.findByOrderId(orderid).isEmpty()) {
			dao.deleteByOrderId(orderid);
			return true;
		}
		
		return false;
	}

	@Override
	public List<Orders> findbyuser(Customer use) {
		// TODO Auto-generated method stub
		return dao.findByUser(use);
	}

	@Override
	public List<Orders> findByDeliveryPerson(Customer deliveryPerson) {
		// TODO Auto-generated method stub
		return dao.findByDeliveryPerson(deliveryPerson);
	}


	

	

}
