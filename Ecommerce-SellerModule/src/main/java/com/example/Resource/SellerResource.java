package com.example.Resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.LoginRequestDto;
import com.example.Dto.RegisterUserRequestDto;
import com.example.Dto.UserDto;
import com.example.Dto.UserResponseDto;
import com.example.Service.AddressService;
import com.example.Service.ProductService;
import com.example.Service.SellerService;
import com.example.entity.Address;
import com.example.entity.Product;
import com.example.entity.Seller;



@Service
public class SellerResource {
	@Autowired
	SellerService ss;
	@Autowired
	AddressService ads;
	@Autowired
	ProductService ps;
	

	public ResponseEntity<CommonApiResponse> registerseller(RegisterUserRequestDto dto) {
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
	    List<Seller> usersWithEmail = ss.findbyemailid(dto.getEmailId());
	    if (!usersWithEmail.isEmpty()) {
	        api.setResponseMessage("Email already registered. Please use another email address.");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }

	   
	    
	    List<Seller> findbyphoneNo = ss.findbyphoneNo(dto.getPhoneNo());
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
	    	 api.setResponseMessage("Failed to register seller address");
		        api.setSuccess(false);
		        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Convert DTO to User entity
	    Seller user = RegisterUserRequestDto.toUserEntity(dto);
	    if (user == null) {
	        api.setResponseMessage("Failed to convert user details");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Set user role and status
	    user.setRole("Seller");
	    user.setStatus("Active");

	    
	    user.setAddress(save);
	    // Add the admin user
	    Seller addedUser = ss.addSeller(user);
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


	public ResponseEntity<CommonApiResponse> updateSeller(RegisterUserRequestDto dto) {
	    CommonApiResponse api = new CommonApiResponse();

	    if (dto == null || dto.getEmailId() == null || dto.getEmailId().isEmpty()) {
	        api.setResponseMessage("Missing input or email ID");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
	    }

	    // Fetch user(s) by email ID
	    List<Seller> users = ss.findbyemailid(dto.getEmailId());

	    if (users == null || users.isEmpty()) {
	        api.setResponseMessage("User not found with provided email ID");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.NOT_FOUND);
	    }

	    Seller userToUpdate = users.get(0); // Assuming the first user is the one to update

	    // Update existing user's fields with the new data from DTO
	    // Preserve the original user's ID and other unique identifiers
	    // Only update the fields that are meant to be updated

	    if (dto.getFirstName() != null) {
	        userToUpdate.setFirstName(dto.getFirstName());
	    }
	    if (dto.getPhoneNo() != null) {
	        userToUpdate.setPhoneNo(dto.getPhoneNo());
	    }
	    // Add other fields to update as required

	    // Update the user
	    Seller updatedUser = ss.update(userToUpdate);

	    if (updatedUser == null) {
	        api.setResponseMessage("Failed to update user");
	        api.setSuccess(false);
	        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    api.setResponseMessage("User updated successfully");
	    api.setSuccess(true);
	    return new ResponseEntity<CommonApiResponse>(api, HttpStatus.OK);
	}

	
	public ResponseEntity<CommonApiResponse> deleteById(int id) {
	    CommonApiResponse api = new CommonApiResponse();
	    String status="Active";
	    if (id <= 0) {
	        api.setResponseMessage("Invalid ID");
	        api.setSuccess(false);
	        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	    }
	    
	    Seller seller = ss.findByIdAndStatus(id, status);
	    if (seller==null) {
	        api.setResponseMessage("Seller with ID " + id + " not found");
	        api.setSuccess(false);
	        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
	    }
	    seller.setStatus("Deactivate");
	    Seller update = ss.update(seller);
	    
	    List<Product> bySeller = ps.findBySeller(seller);
	    
	    for(Product pro:bySeller) {
	    	pro.setStatus("Deactivate");
	    	ps.update(pro);
	    }
	    
	    if(update==null) {
	    	api.setResponseMessage("failed to delete seller");
	   	 api.setSuccess(false);
	   	 return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	   
	 api.setResponseMessage("succcessfully  deleted seller");
	 api.setSuccess(false);
	 return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
public ResponseEntity<UserResponseDto> getUsersByRole() {
    UserResponseDto response = new UserResponseDto();
String role="Seller";

String status="Active";
    // Check if role is null or empty
    if (role == null || role.isEmpty()) {
        response.setResponseMessage("Missing role");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    List<Seller> users = ss.findByStatusAndRole(status, role);

    if (users.isEmpty()) {
        response.setResponseMessage("No Users Found");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    List<UserDto> userDtos = new ArrayList<>();

    for (Seller user : users) {
        UserDto dto = UserDto.toUserDtoEntity(user);

        

        userDtos.add(dto);
    }

    response.setUsers(userDtos);
    response.setResponseMessage("Users Fetched Successfully");
    response.setSuccess(true);

    return new ResponseEntity<>(response, HttpStatus.OK);
}


public ResponseEntity<CommonApiResponse>login(LoginRequestDto dto){
	
	CommonApiResponse api = new CommonApiResponse();
	
	if(dto==null) {
		api.setResponseMessage("missing input");
		api.setSuccess(false);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
	}
	
	List<Seller> login = ss.login(dto.getRole(), dto.getEmailid(), dto.getPassword());
	
	if(login.isEmpty()) {
		api.setResponseMessage("login failed check the details");
		api.setSuccess(false);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
	}else
		api.setResponseMessage("login success");
	api.setSuccess(true);
	return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
}
}

