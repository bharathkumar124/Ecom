package com.example.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.CustomerOrder;
import java.util.List;


@Repository
public interface CustomerOrderDao extends JpaRepository<CustomerOrder, Integer>{
CustomerOrder findByOrderid(String orderid);

List<CustomerOrder> findByCustomerid(int customerid);

List<CustomerOrder> findByCustomeridAndDeliverystatus(int customerid, String deliverystatus);







}
