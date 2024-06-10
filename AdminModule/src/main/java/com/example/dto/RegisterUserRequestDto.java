package com.example.dto;

import org.springframework.beans.BeanUtils;

import com.example.entity.Admin;

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
	
	public static Admin toUserEntity(RegisterUserRequestDto registerUserRequestDto) {
		Admin user = new Admin();
		BeanUtils.copyProperties(registerUserRequestDto, user);
		return user;
	}
}
