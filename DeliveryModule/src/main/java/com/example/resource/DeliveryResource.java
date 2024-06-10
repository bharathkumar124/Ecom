package com.example.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.CommonApiResponse;
import com.example.dto.RegisterUserRequestDto;
import com.example.dto.UserDto;
import com.example.dto.UserResponseDto;
import com.example.entity.Address;
import com.example.entity.DeliveryPerson;
import com.example.entity.Seller;
import com.example.service.AddressService;
import com.example.service.DeliveryService;
import com.example.service.SellerService;




@Service
public class DeliveryResource {
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@Autowired
	DeliveryService ds;
	
	@Autowired
	SellerService ss;
	
	@Autowired
	AddressService ads;
	
	public ResponseEntity<CommonApiResponse> addDeliveryper(RegisterUserRequestDto dto){
		
		
		CommonApiResponse api = new CommonApiResponse();
		
		if(dto==null) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getEmailId()==null) {
			api.setResponseMessage("Email is missing");
			api.setSuccess(false);
			 return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		DeliveryPerson userEntity = RegisterUserRequestDto.toUserEntity(dto);
		
		userEntity.setRole("DeliveryPerson");
		userEntity.setStatus("Active");
		
		userEntity.setPassword(encoder.encode(dto.getPassword()));
		Seller findbyid = ss.findbyid(dto.getSellerId());
		
		if(findbyid==null) {
			api.setResponseMessage("seller is not found");
			api.setSuccess(false);
			 return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		userEntity.setSeller(findbyid);
		
Address add=new Address();
	    
	    add.setCity(dto.getCity());
	    add.setPincode(dto.getPincode());
	    add.setStreet(dto.getStreet());
	    
	    Address save = ads.save(add);
	    
	    if(save==null) {
	    	 api.setResponseMessage("Failed to register delivry address");
		        api.setSuccess(false);
		        return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    userEntity.setAddress(add);
	    DeliveryPerson addd= ds.adddeliverper(userEntity);
		
		if(addd==null) {
			api.setResponseMessage("failed to register delivery person");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}else
			
			api.setResponseMessage("delivery person register success");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		
	}
	
	public ResponseEntity<UserResponseDto> findDeliveryBySellerId(int sellerId) {
	    UserResponseDto api = new UserResponseDto();

	    if (sellerId <= 0) {
	        api.setResponseMessage("Please check the seller ID");
	        api.setSuccess(false);
	        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	    }

	 Seller seller = ss.findbyid(sellerId);    // Assuming ds.findbyid is replaced with findById
	    if (seller == null) {
	        api.setResponseMessage("Seller not found");
	        api.setSuccess(false);
	        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND); // Correcting status to NOT_FOUND
	    }

	    List<DeliveryPerson> deliveryPersons = ds.findbysellerroleAndStatus(seller, "DeliveryPerson","Active");
	    if (deliveryPersons.isEmpty()) {
	        api.setResponseMessage("No delivery persons found");
	        api.setSuccess(false);
	        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND); // Correcting status to NOT_FOUND
	    }

	    List<UserDto> users = new ArrayList<>();
	    for (DeliveryPerson deliveryPerson : deliveryPersons) {
	        UserDto userDto = UserDto.toUserDtoEntity(deliveryPerson);
	        users.add(userDto);
	    }
	    api.setUsers(users);
	    api.setResponseMessage("Data fetched successfully");
	    api.setSuccess(true);
	    return new ResponseEntity<>(api, HttpStatus.OK);
	}
	
	
public ResponseEntity<CommonApiResponse>deletebyid(int id){
		
	String status="Active";
		CommonApiResponse api = new CommonApiResponse();
		if(id==0) {
			api.setResponseMessage("id is null");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		DeliveryPerson byIdAndStatus = ds.findByIdAndStatus(id, status);
		
		if(byIdAndStatus==null) {
			api.setResponseMessage(id+" id not found");
			api.setSuccess(true);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		}
		
		DeliveryPerson update = ds.update(byIdAndStatus);
		
		if(update==null) {
			api.setResponseMessage(id+" id delted failed");
			api.setSuccess(true);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		}else
			api.setResponseMessage("id deleted successfull");
		api.setSuccess(false);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
	}


public ResponseEntity<UserResponseDto> getUsersByRole() {
    UserResponseDto response = new UserResponseDto();
String role="DeliveryPerson";
    // Check if role is null or empty
    if (role == null || role.isEmpty()) {
        response.setResponseMessage("Missing role");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    List<DeliveryPerson> users = ds.findall();

    if (users.isEmpty()) {
        response.setResponseMessage("No Users Found");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    List<UserDto> userDtos = new ArrayList<>();

    for (DeliveryPerson user : users) {
        UserDto dto = UserDto.toUserDtoEntity(user);

        

        userDtos.add(dto);
    }

    response.setUsers(userDtos);
    response.setResponseMessage("Users Fetched Successfully");
    response.setSuccess(true);

    return new ResponseEntity<>(response, HttpStatus.OK);
}
}
