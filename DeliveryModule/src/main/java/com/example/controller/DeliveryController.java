package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.OrdersFeign;
import com.example.dto.CommonApiResponse;
import com.example.dto.OrderResponseDto;
import com.example.dto.RegisterUserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.resource.DeliveryResource;



@RestController
@RequestMapping("/deliveryperson")
public class DeliveryController {
	
	@Autowired
	DeliveryResource resource;
	
	@Autowired
	OrdersFeign feign;
	
	@PostMapping(value = "/add")
	public ResponseEntity<CommonApiResponse> addDeliveryPerson(@RequestBody RegisterUserRequestDto dto){
	return	resource.addDeliveryper(dto);
	}

	
	@GetMapping(value = "/fectbySellerid")
	public ResponseEntity<UserResponseDto> findbySellerid(@RequestParam int sellerid){
		return resource.findDeliveryBySellerId(sellerid);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<CommonApiResponse>deltebyid(@RequestParam int id){
		return resource.deletebyid(id);
	}
	
	@GetMapping(value = "/findalldelivery")
	public ResponseEntity<UserResponseDto>findalldelivery(){
		return this.resource.getUsersByRole();
	}
	
	@GetMapping(value = "/finddeliverypersonorders")
	public ResponseEntity<OrderResponseDto>findbydeliveryid(@RequestParam int id){
		return this.feign.findbydeliveryid(id);
	}
}
