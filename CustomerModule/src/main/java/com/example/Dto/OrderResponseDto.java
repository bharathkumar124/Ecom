package com.example.Dto;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.CustomerOrder;
import com.example.entity.DeliveryPersonOrder;
import com.example.entity.Orders;
import com.example.entity.SellerOrders;

public class OrderResponseDto extends CommonApiResponse {
	
	private List<OrderDto>orders=new ArrayList<>();
	
	private List<Orders>or=new ArrayList<>();
	
	private List<CustomerOrder> cusprder=new ArrayList<>();
	
	private List<SellerOrders>listseller=new ArrayList<>();
	
	private List<DeliveryPersonOrder>listdelivery=new ArrayList<>();

	public List<DeliveryPersonOrder> getListdelivery() {
		return listdelivery;
	}

	public void setListdelivery(List<DeliveryPersonOrder> listdelivery) {
		this.listdelivery = listdelivery;
	}

	public List<SellerOrders> getListseller() {
		return listseller;
	}

	public void setListseller(List<SellerOrders> listseller) {
		this.listseller = listseller;
	}

	public List<CustomerOrder> getCusprder() {
		return cusprder;
	}

	public void setCusprder(List<CustomerOrder> cusprder) {
		this.cusprder = cusprder;
	}

	public List<Orders> getOr() {
		return or;
	}

	public void setOr(List<Orders> or) {
		this.or = or;
	}

	public List<OrderDto> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDto> orders) {
		this.orders = orders;
	}

	

}
