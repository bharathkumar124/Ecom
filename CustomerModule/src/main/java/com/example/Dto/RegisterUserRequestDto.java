package com.example.Dto;

import org.springframework.beans.BeanUtils;

import com.example.entity.Customer;

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
	
	public static Customer toUserEntity(RegisterUserRequestDto registerUserRequestDto) {
		Customer user = new Customer();
		BeanUtils.copyProperties(registerUserRequestDto, user);
		return user;
	}
}
