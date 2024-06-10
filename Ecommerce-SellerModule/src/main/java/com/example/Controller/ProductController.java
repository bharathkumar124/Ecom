package com.example.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.ProductResponseDto;
import com.example.Resource.ProductResource;



@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductResource resource;
	
	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addpro(@RequestParam("name") String name, @RequestParam String description,
			@RequestParam BigDecimal price, @RequestParam int quantity, @RequestParam String status,
			@RequestParam MultipartFile image1, @RequestParam MultipartFile image2,
			@RequestParam MultipartFile image3,@RequestParam int sellerid) throws IOException{
		return resource.addproduct(name, description, price, quantity, status, image1, image2, image3,sellerid);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<CommonApiResponse> updatepro(@RequestParam int id,@RequestParam("name") String name, @RequestParam String description,
			@RequestParam BigDecimal price, @RequestParam int quantity, @RequestParam String status,
			@RequestParam MultipartFile image1, @RequestParam MultipartFile image2,
			@RequestParam MultipartFile image3) throws IOException{
		return resource.updateproduct(id, name, description, price, quantity, status, image1, image2, image3);
	}
	
	
	  @GetMapping(value = "/allproducts") public ResponseEntity<ProductResponseDto>findall(){ 
		  return resource.findalldata();
}
	 
	  @GetMapping("/image")
	    public ResponseEntity<byte[]> getProductImage(@RequestParam int productId) throws IOException, DataFormatException {
	        byte[] imageData = resource.getImageDataById(productId);
	        
	        
	        
	        if (imageData != null) {
	            return ResponseEntity.ok()
	                    .contentType(MediaType.IMAGE_JPEG)
	                    .body(imageData);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	

	
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<CommonApiResponse> deleteproduct(@RequestParam int id){
		return resource.deletebyid(id);
	}
	
	@GetMapping(value = "/findbyseller")
	public ResponseEntity<ProductResponseDto>findbysellerid(@RequestParam int sellerid){
		return resource.findbyseller(sellerid);
	}
}
