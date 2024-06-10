package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.OrderResponseDto;


@FeignClient("CustomerModule")
public interface OrdersFeign {
	@GetMapping(value = "/orders/finddeliverypersonorders")
	public ResponseEntity<OrderResponseDto>findbydeliveryid(@RequestParam int id);
	
}
