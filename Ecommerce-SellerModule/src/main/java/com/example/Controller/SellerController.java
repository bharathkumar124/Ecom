package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DeliverFeign;
import com.example.Dto.CommonApiResponse;
import com.example.Dto.LoginRequestDto;
import com.example.Dto.RegisterUserRequestDto;
import com.example.Dto.UserResponseDto;
import com.example.Resource.SellerResource;


@RestController
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	SellerResource resource;
	
	@Autowired
	DeliverFeign feign;
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<CommonApiResponse> addseller(@RequestBody RegisterUserRequestDto dto){
		return resource.registerseller(dto);
	}

	
	@PutMapping(value = "/update")
	public ResponseEntity<CommonApiResponse> updateSeller(@RequestBody RegisterUserRequestDto dto){
		return resource.updateSeller(dto);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<CommonApiResponse>deleteseller(@RequestParam int id){
		return resource.deleteById(id);
	}
	
	@GetMapping(value = "/findallsellers")
	
	public ResponseEntity<UserResponseDto>fetchseller(){
		return resource.getUsersByRole();
	}
	
	@GetMapping(value = "/login")
	public ResponseEntity<CommonApiResponse> login(@RequestBody LoginRequestDto dto){
		return resource.login(dto);
	}
	
	
	@PostMapping(value = "/deliverypersonadd")
	public ResponseEntity<CommonApiResponse> addDeliveryPerson(@RequestBody RegisterUserRequestDto dto){
		return feign.addDeliveryPerson(dto);
	}
	
	@GetMapping(value = "/fectbySellerid")
	public ResponseEntity<UserResponseDto> findbySellerid(@RequestParam int sellerid){
		return feign.findbySellerid(sellerid);
	}
	
}
