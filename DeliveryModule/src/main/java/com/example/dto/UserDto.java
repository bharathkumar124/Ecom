package com.example.dto;

import org.springframework.beans.BeanUtils;

import com.example.entity.Address;
import com.example.entity.DeliveryPerson;


import lombok.Data;


@Data
public class UserDto {

	private int id;

	private String firstName;

	private String lastName;

	private String emailId;

	private String phoneNo;

	private String role;

	private Address address;

	private UserDto seller;

	private String status;

	public static UserDto toUserDtoEntity(DeliveryPerson user) {
UserDto userDto = new UserDto();
        
        // Copying simple properties while avoiding nested objects
        BeanUtils.copyProperties(user, userDto, "seller", "address");

        // Handling Address separately if it exists
        if (user.getAddress() != null) {
            userDto.setAddress(user.getAddress());
        }

        // Handling Seller separately if it exists
        if (user.getSeller() != null) {
            // Create a new UserDto to hold Seller information
            UserDto sellerDto = new UserDto();
            // Copy Seller's properties while avoiding infinite recursion
            BeanUtils.copyProperties(user.getSeller(), sellerDto, "seller", "address");
            userDto.setSeller(sellerDto);  // Assign to UserDto's seller
        }

		return userDto;
	}
}
