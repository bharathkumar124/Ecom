package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.CommonApiResponse;
import com.example.dto.ProductResponseDto;
import com.example.dto.UserResponseDto;


@FeignClient("Ecommerce-SellerModule")
public interface SellerFeign {

@GetMapping(value = "/seller/findallsellers")
	
	public ResponseEntity<UserResponseDto>fetchseller();

@DeleteMapping(value = "/seller/delete")
public ResponseEntity<CommonApiResponse>deleteseller(@RequestParam int id);

@GetMapping(value = "/product/allproducts")
public ResponseEntity<ProductResponseDto>findall();
}
