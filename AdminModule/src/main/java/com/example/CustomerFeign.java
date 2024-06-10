package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.dto.UserResponseDto;
import com.example.entity.OrderResponseDto;

@FeignClient("CustomerModule")
public interface CustomerFeign {
	@GetMapping(value = "/customer/allcustomer")
	public ResponseEntity<UserResponseDto>allcustomers();
	
	@GetMapping(value = "/orders/allorders")
	public ResponseEntity<OrderResponseDto>findallorders();
}
