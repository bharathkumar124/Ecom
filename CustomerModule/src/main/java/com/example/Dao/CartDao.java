package com.example.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Cart;
import com.example.entity.Customer;


@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {
	List<Cart> findByUser(Customer user);

}
