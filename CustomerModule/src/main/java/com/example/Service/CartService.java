package com.example.Service;

import java.util.List;

import com.example.entity.Cart;
import com.example.entity.Customer;


public interface CartService {
	
	Cart addcart(Cart ca);
	
	Cart findbyid(int id);
	
	void delete(Cart cart);
	List<Cart> findByUser(Customer user);


}
