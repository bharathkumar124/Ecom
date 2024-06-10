package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.RegisterUserRequestDto;
import com.example.Dto.UserResponseDto;
@FeignClient("DeliveryModule")
public interface DeliverFeign {
	@PostMapping(value = "/deliveryperson/add")
	public ResponseEntity<CommonApiResponse> addDeliveryPerson(@RequestBody RegisterUserRequestDto dto);
	
	@GetMapping(value = "/deliveryperson/fectbySellerid")
	public ResponseEntity<UserResponseDto> findbySellerid(@RequestParam int sellerid);
}
