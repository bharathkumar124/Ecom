package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.dto.CommonApiResponse;





public class OrderResponseDto extends CommonApiResponse {
	
	private List<OrderDto>orders=new ArrayList<>();

	public List<OrderDto> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDto> orders) {
		this.orders = orders;
	}
	
	

}
