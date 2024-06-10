package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CategoryResponseDto;
import com.example.dto.CommonApiResponse;
import com.example.entity.Category;
import com.example.resource.CategoryResource;


@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryResource resource;
	
	@PostMapping(value = "/add")
	public ResponseEntity<CommonApiResponse> addcategory(@RequestBody Category cat){
		return resource.addcategory(cat);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<CommonApiResponse> updatecategory(@RequestBody Category cat){
		return resource.updatecategory(cat);
	}
	
	@GetMapping(value = "/fetchall")
	public ResponseEntity<CategoryResponseDto> fetalldata(){
		return resource.findalldata();
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<CommonApiResponse>deletebyid(@RequestParam int id){
		return resource.deletebyid(id);
	}
}
