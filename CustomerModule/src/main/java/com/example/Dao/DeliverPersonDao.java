package com.example.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.DeliveryPerson;

@Repository
public interface DeliverPersonDao extends JpaRepository<DeliveryPerson, Integer>{

}
