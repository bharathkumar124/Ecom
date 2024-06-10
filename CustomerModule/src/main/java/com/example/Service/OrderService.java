package com.example.Service;



import java.util.List;

import com.example.entity.Customer;
import com.example.entity.Orders;


public interface OrderService {
	List<Orders> addOrder(List<Orders> orders);
	
	Orders updateorder(Orders order); 
	
	List<Orders> fidbyid(String orderid);
	
	List<Orders>fetchall();
	
	boolean delete(String orderid);
	
	List<Orders>findbyuser(Customer use);
	List<Orders> findByDeliveryPerson(Customer deliveryPerson);
	

}
