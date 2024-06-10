package com.example.Dto;

import lombok.Data;

@Data
public class OrderDto {

	private String orderid;
	
	private String Deliveryaddress;
	
	private int quantity;
	
	private String totalamount;
	
	private int productid;
	private String productname;
	
	private String deliverystatus;
	
	private String deliverydate;
	
	private int delipersonid;
	
	private int customerid;
	private int sellerid;
	
}
