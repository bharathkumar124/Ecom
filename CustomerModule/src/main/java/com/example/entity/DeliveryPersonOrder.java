package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DeliveryPersonOrder {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 private String orderid;
	 
	 private int deliverpersonid;
	 private String deliverypersonname;
	 
	 private String DeliveryAddress;
	
	 private String customername;
	 
	 private String deliverydate;
	 private String expecteddeliverydate;
	 
	 private String deliverystatus;
	 
	private int sellerid;
	 
	 private int customerid;
	 
	 
	private String productname;
	
	private int quantity;
	
	private String totalamount;

}
