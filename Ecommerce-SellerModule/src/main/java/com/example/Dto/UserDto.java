package com.example.Dto;

import org.springframework.beans.BeanUtils;

import com.example.entity.Address;
import com.example.entity.Seller;

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

	public static UserDto toUserDtoEntity(Seller user) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto, "seller");
		return userDto;
	}
}
