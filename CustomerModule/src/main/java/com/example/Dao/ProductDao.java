package com.example.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Product;



@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{

}
