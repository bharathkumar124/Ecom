package com.example.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.SellerOrders;
import java.util.List;


@Repository
public interface SellerOrderDao extends JpaRepository<SellerOrders, Integer>{
SellerOrders findByOrOrderid(String orderid);

void deleteByOrOrderid(String orderid);

List<SellerOrders> findBySellerid(int sellerid);

List<SellerOrders> findBySelleridAndOrderid(int sellerid, String orderid);
}

