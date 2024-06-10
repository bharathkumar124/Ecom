package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.SellerOrderDao;
import com.example.entity.SellerOrders;

@Service
public class SellerOrderService {

	@Autowired
	SellerOrderDao dao;
	
	public List<SellerOrders>saveall(List<SellerOrders> orders){
		return dao.saveAll(orders);
	}
	
	public SellerOrders updateadd(SellerOrders orders) {
		return dao.save(orders);
	}
	
	public SellerOrders findbyorderid(String orderid) {
		return dao.findByOrOrderid(orderid);
	}
	public boolean delete(String orderid) {
		SellerOrders byOrOrderid = dao.findByOrOrderid(orderid);
		
		if(byOrOrderid!=null) {
			dao.deleteByOrOrderid(orderid);
			return true;
		}else
			return false;
	}
	
	public List<SellerOrders>fetchallbyid(int sellerid){
		return dao.findBySellerid(sellerid);
	}
	
	 public List<SellerOrders> findBySelleridAndOrderid(int sellerid, String orderid){
		 return dao.findBySelleridAndOrderid(sellerid, orderid);
	 }
}
