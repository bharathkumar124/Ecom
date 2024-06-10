package com.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SellerOrders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 private String orderid;
	 
	 private String DeliveryAddress;
	 
	 private int customerid;
	
	 private String customername;
	 
	 private String expecteddeliverydate;
	 
	 private String deliverydate;
	 
	 private String deliverystatus;
	 
	 @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;
	 
	 @ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "delivery_id")
	 private DeliveryPerson delivery;
	
	private int quantity;
	
	private String totalamount;
	
	private int sellerid;
}
