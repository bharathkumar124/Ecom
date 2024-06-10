package com.example.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.CommonApiResponse;
import com.example.dto.RegisterUserRequestDto;
import com.example.entity.Address;
import com.example.entity.Admin;
import com.example.service.AddressService;
import com.example.service.UserService;

@Service
public class UserResource {
	@Autowired
	UserService us;
	
	@Autowired
	AddressService ads;
	
	@Autowired
	PasswordEncoder psdencode;

	
	public ResponseEntity<CommonApiResponse> registerAdmin(RegisterUserRequestDto dto) {
	    CommonApiResponse api = new CommonApiResponse();

	    // Check if the DTO is null
	    if (dto == null) {
	        api.setResponseMessage("User details are null");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }

	    // Check if email is provided
	    if (dto.getEmailId() == null || dto.getEmailId().isEmpty()) {
	        api.setResponseMessage("Email address is required");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }

	    // Check if email is already registered
	    List<Admin> usersWithEmail = us.findbyemail(dto.getEmailId());
	    if (!usersWithEmail.isEmpty()) {
	        api.setResponseMessage("Email already registered. Please use another email address.");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }

	    // Check if firstname and lastname combination is already registered
	    List<Admin> usersWithFullName = us.findbyfirstnameAndlastname(dto.getFirstName(), dto.getLastName());
	    if (!usersWithFullName.isEmpty()) {
	        api.setResponseMessage("First name and last name combination is already registered. Please use another name.");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }
	    
	    List<Admin> findbyphoneNo = us.findbyphoneNo(dto.getPhoneNo());
	    if(!findbyphoneNo.isEmpty()) {
	    	 api.setResponseMessage("phoneNo already registered. Please use another PhoneNumber .");
		        api.setSuccess(false);
		        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }
Address add=new Address();
	    
	    add.setCity(dto.getCity());
	    add.setPincode(dto.getPincode());
	    add.setStreet(dto.getStreet());
	    
	    Address save = ads.save(add);
	    
	    if(save==null) {
	    	 api.setResponseMessage("Failed to register admin address");
		        api.setSuccess(false);
		        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Convert DTO to User entity
	    Admin user = RegisterUserRequestDto.toUserEntity(dto);
	    if (user == null) {
	        api.setResponseMessage("Failed to convert user details");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Set user role and status
	    user.setRole("Admin");
	    user.setStatus("Active");
	    user.setPassword(psdencode.encode(dto.getPassword()));

	    
	    user.setAddress(save);
	    // Add the admin user
	    Admin addedUser = us.add(user);
	    if (addedUser == null) {
	        api.setResponseMessage("Failed to register admin user");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	  	    
	    // Registration successful
	    api.setResponseMessage("Registration successful");
	    api.setSuccess(true);
	    return new ResponseEntity<CommonApiResponse>(api, HttpStatus.OK);
	}

	
}
