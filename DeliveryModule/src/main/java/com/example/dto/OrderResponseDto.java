package com.example.dto;

import java.util.ArrayList;
import java.util.List;


import com.example.entity.DeliveryPersonOrder;



public class OrderResponseDto extends CommonApiResponse {
	
	
	
	private List<DeliveryPersonOrder>listdelivery=new ArrayList<>();

	public List<DeliveryPersonOrder> getListdelivery() {
		return listdelivery;
	}

	public void setListdelivery(List<DeliveryPersonOrder> listdelivery) {
		this.listdelivery = listdelivery;
	}

	
}
