package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dao.CartDao;
import com.example.entity.Cart;
import com.example.entity.Customer;


@Service
public class CartServiceimpl implements CartService{
	
	@Autowired
	CartDao dao;

	@Override
	public Cart addcart(Cart ca) {
		// TODO Auto-generated method stub
		return dao.save(ca);
	}

	@Override
	public Cart findbyid(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	

	@Override
	public void delete(Cart cart) {
		dao.delete(cart);
		
	}

	@Override
	public List<Cart> findByUser(Customer user) {
		// TODO Auto-generated method stub
		return dao.findByUser(user);
	}

}
