package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String orderId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer user;
	
	@ManyToOne
	@JoinColumn(name = "seller_user_id")
	private Seller seller;

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private int quantity;

	private String orderTime;
	
private String DeliverAddress;

private String expecteddeliveryDate;
private String totalamount;

private String status; // Processing, Delivered, On the Way // this will be for customer

// delivery properties

// updated by seller
@ManyToOne
@JoinColumn(name = "delivery_person_id")
private DeliveryPerson deliveryPerson;

// updated by delivery person
private String deliveryTime; // Evening, Morning, Afternoon, Night

private String deliveryDate;

private String deliveryStatus; // Delivered, Pending // this will be for actual delivery status

	public String getTotalamount() {
	return totalamount;
}

public void setTotalamount(String totalamount) {
	this.totalamount = totalamount;
}

	public String getExpecteddeliveryDate() {
	return expecteddeliveryDate;
}

public void setExpecteddeliveryDate(String expecteddeliveryDate) {
	this.expecteddeliveryDate = expecteddeliveryDate;
}

	public String getDeliverAddress() {
	return DeliverAddress;
}

public void setDeliverAddress(String deliverAddress) {
	DeliverAddress = deliverAddress;
}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public DeliveryPerson getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
