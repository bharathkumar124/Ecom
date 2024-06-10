package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dto.CartRequestDto;
import com.example.Dto.CartResponseDto;
import com.example.Dto.CommonApiResponse;
import com.example.Resource.CartResource;
import com.example.Service.UserService;



@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartResource resource;
	
	@Autowired
	UserService us;
	
	@PostMapping("/addcart")
	public ResponseEntity<CommonApiResponse> addcart(@RequestBody CartRequestDto dto){
		return this.resource.addcart(dto);
	}
	
	
	@DeleteMapping(value = "/delete")
	
	public ResponseEntity<CommonApiResponse> delete(@RequestParam int userid,@RequestParam int id){
		return this.resource.deletecart(userid,id);
	}

	
	@GetMapping("/fetch")
	// @Operation(summary = "Api to fetch the user cart")
	public ResponseEntity<CartResponseDto> fetchUserCart(@RequestParam("userId") int userId) {
		return resource.fetchUserCartDetails(userId);
	}
	
}
