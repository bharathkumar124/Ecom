package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.CustomerOrderDao;
import com.example.entity.CustomerOrder;

@Service
public class CustomerOrderService {
	
	@Autowired
	CustomerOrderDao dao;
	
	public List<CustomerOrder> savedata(List<CustomerOrder> order) {
		return dao.saveAll(order);
	}
	
	public CustomerOrder findByOrderid(String orderid){
		return dao.findByOrderid(orderid);
	}
	
	public CustomerOrder updateaddress(CustomerOrder customerorder) {
		return dao.save(customerorder);
	}
	
	public List<CustomerOrder> findall(){
		return dao.findAll();
	}
	public List<CustomerOrder> findByCustomerid(int customerid){
		return dao.findByCustomerid(customerid);

	}
	
	public boolean delete(CustomerOrder order) {
		CustomerOrder byOrderid = dao.findByOrderid(order.getOrderid());
		if(byOrderid!=null) {
			dao.delete(byOrderid);
			return true;
		}else
			return false;
	}
	
	public CustomerOrder updatedata(CustomerOrder order) {
		return dao.save(order);
	}
	 public List<CustomerOrder> findByCustomeridAndDeliverystatus(int customerid, String deliverystatus){
		 return dao.findByCustomeridAndDeliverystatus(customerid, deliverystatus);
	 }

}
