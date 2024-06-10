package com.example.dto;

import org.springframework.beans.BeanUtils;

import com.example.entity.DeliveryPerson;


import lombok.Data;

@Data
public class RegisterUserRequestDto {
	private String firstName;

	private String lastName;

	private String emailId;

	private String password;

	private String phoneNo;

	private String role;

	private String street;

	private String city;

	private int pincode;

	private int sellerId; // seller id for delivery person
	
	public static DeliveryPerson toUserEntity(RegisterUserRequestDto registerUserRequestDto) {
		DeliveryPerson user = new DeliveryPerson();
		BeanUtils.copyProperties(registerUserRequestDto, user);
		return user;
	}
}
