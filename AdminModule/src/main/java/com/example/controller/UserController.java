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

import com.example.CustomerFeign;
import com.example.SellerFeign;
import com.example.dto.CommonApiResponse;
import com.example.dto.ProductResponseDto;
import com.example.dto.RegisterUserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.OrderResponseDto;
import com.example.resource.UserResource;


@RestController
@RequestMapping("/admin")
public class UserController {
	@Autowired
	UserResource resource;
	
	@Autowired
	SellerFeign feign;
	
	@Autowired
	CustomerFeign customerfeign;
	
	
	@PostMapping(value = "/registeradmin")
	
	public ResponseEntity<CommonApiResponse> reister(@RequestBody RegisterUserRequestDto usergerister){
		return resource.registerAdmin(usergerister);
	}
	

	@GetMapping(value = "/allsellers")
	public ResponseEntity<UserResponseDto>fetchseller(){
	return feign.fetchseller();
}
	@DeleteMapping(value = "/deleteseller")
	public ResponseEntity<CommonApiResponse>deleteseller(@RequestParam int id){
		return feign.deleteseller(id);
	}
	
	@GetMapping(value = "allproducts")
	public ResponseEntity<ProductResponseDto>findallproducts(){
		return feign.findall();
	}
	
	@GetMapping(value = "/allcustomer")
	public ResponseEntity<UserResponseDto>allcustomers(){
		return customerfeign.allcustomers();
	}
	
	@GetMapping(value = "/allorders")
	public ResponseEntity<OrderResponseDto>findallorders(){
		return customerfeign.findallorders();
	}
}
