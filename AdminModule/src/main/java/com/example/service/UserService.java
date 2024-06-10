package com.example.service;

import java.util.List;

import com.example.entity.Admin;



public interface UserService {
Admin add(Admin u);

List<Admin>findbyemail(String email);

List<Admin> findbyfirstnameAndlastname(String first, String last);

List<Admin> findbyphoneNo(String phoneNo);
}
