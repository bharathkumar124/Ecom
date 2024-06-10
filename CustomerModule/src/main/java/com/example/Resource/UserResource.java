package com.example.Resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.RegisterUserRequestDto;
import com.example.Dto.UserDto;
import com.example.Dto.UserResponseDto;
import com.example.Service.AddressService;
import com.example.Service.OrderService;
import com.example.Service.UserService;
import com.example.entity.Address;
import com.example.entity.Customer;



@Service
public class UserResource {
	@Autowired
	UserService us;
	
	@Autowired
	AddressService ads;
	
	@Autowired
	OrderService os;
	
	@Autowired
	PasswordEncoder pwdecoder;
	


	
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
	    List<Customer> usersWithEmail = us.findbyemail(dto.getEmailId());
	    if (!usersWithEmail.isEmpty()) {
	        api.setResponseMessage("Email already registered. Please use another email address.");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }

	    // Check if firstname and lastname combination is already registered
	    List<Customer> usersWithFullName = us.findbyfirstnameAndlastname(dto.getFirstName(), dto.getLastName());
	    if (!usersWithFullName.isEmpty()) {
	        api.setResponseMessage("First name and last name combination is already registered. Please use another name.");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }
	    
	    List<Customer> findbyphoneNo = us.findbyphoneNo(dto.getPhoneNo());
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
	    	 api.setResponseMessage("Failed to register Customer address");
		        api.setSuccess(false);
		        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Convert DTO to User entity
	    Customer user = RegisterUserRequestDto.toUserEntity(dto);
	    if (user == null) {
	        api.setResponseMessage("Failed to convert user details");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Set user role and status
	    user.setRole("Customer");
	    user.setStatus("Active");
	    
	    user.setPassword(pwdecoder.encode(dto.getPassword()));

	    
	    user.setAddress(save);
	    // Add the admin user
	    Customer addedUser = us.add(user);
	    if (addedUser == null) {
	        api.setResponseMessage("Failed to register Customer user");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	  	    
	    // Registration successful
	    api.setResponseMessage("Registration successful");
	    api.setSuccess(true);
	    return new ResponseEntity<CommonApiResponse>(api, HttpStatus.OK);
	}

	

public ResponseEntity<UserResponseDto> getUsersByRole() {
    UserResponseDto response = new UserResponseDto();
String role="Customer";
    // Check if role is null or empty
    if (role == null || role.isEmpty()) {
        response.setResponseMessage("Missing role");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    List<Customer> users = us.findByallcustomer(role);

    if (users.isEmpty()) {
        response.setResponseMessage("No Users Found");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    List<UserDto> userDtos = new ArrayList<>();

    for (Customer user : users) {
        UserDto dto = UserDto.toUserDtoEntity(user);

        

        userDtos.add(dto);
    }

    response.setUsers(userDtos);
    response.setResponseMessage("Users Fetched Successfully");
    response.setSuccess(true);

    return new ResponseEntity<>(response, HttpStatus.OK);
}

}
