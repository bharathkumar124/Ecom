package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.RegisterUserRequestDto;
import com.example.Dto.UserResponseDto;
import com.example.Resource.UserResource;



@RestController
@RequestMapping("/customer")
public class UserController {
	@Autowired
	UserResource resource;
	
	
	
	@PostMapping(value = "/register")
	
	public ResponseEntity<CommonApiResponse> reister(@RequestBody RegisterUserRequestDto usergerister){
		return resource.registerAdmin(usergerister);
	}

	
	
	@GetMapping(value = "/allcustomer")
	public ResponseEntity<UserResponseDto>allcustomers(){
		return resource.getUsersByRole();
	}
}
