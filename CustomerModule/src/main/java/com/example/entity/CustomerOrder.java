package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 private String orderid;
	 
	 private String DeliveryAddress;
	
	 private String customername;
	 
	 private String deliverydate;
	 
	 private String deliverystatus;
	 
	 private String deliverypersonName;
	 
	 private String deliverpersonContact;
	 
	 private int customerid;
	 
	 private String expecteddeliverydate;
	 
	private String productname;
	
	private int quantity;
	
	private String totalamount;
	
	 
}
