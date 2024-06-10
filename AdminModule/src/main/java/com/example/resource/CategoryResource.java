package com.example.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dto.CategoryResponseDto;
import com.example.dto.CommonApiResponse;
import com.example.entity.Category;
import com.example.service.CategoryService;



@Service
public class CategoryResource {
	@Autowired
	CategoryService cs;
	
	public ResponseEntity<CommonApiResponse> addcategory(Category cat){
		CommonApiResponse api = new CommonApiResponse();
		if(cat==null) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		cat.setStatus("Active");
		Category category = cs.add(cat);
		
		if(category==null) {
			api.setResponseMessage("failed to add category");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}else
			api.setResponseMessage("category added successfully");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
	}
	
	
	public ResponseEntity<CommonApiResponse> updatecategory(Category cat){
		CommonApiResponse api=new CommonApiResponse();
		
		if(cat==null) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		if(cat.getId()==0) {
			api.setResponseMessage("missing category id");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		cat.setStatus("Active");
		Category update = cs.update(cat);
		if(update==null) {
			api.setResponseMessage("updating data is failed");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}else
			api.setResponseMessage("category updated successfully");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		
	}
	
	public ResponseEntity<CategoryResponseDto> findalldata() {
	    CategoryResponseDto dto = new CategoryResponseDto();

	    // Make sure cs is not null
	    if (cs == null) {
	        dto.setResponseMessage("Service not available");
	        dto.setSuccess(false);
	        return new ResponseEntity<CategoryResponseDto>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    List<Category> catt = cs.getCategoriesByStatusIn(Arrays.asList("Active"));

	    if (catt.isEmpty()) {
	        dto.setResponseMessage("No data found");
	        dto.setSuccess(false);
	        return new ResponseEntity<CategoryResponseDto>(dto, HttpStatus.NOT_FOUND);
	    } else {
	        dto.setcategories(catt);
	        dto.setResponseMessage("Data fetched successfully");
	        dto.setSuccess(true);
	        return new ResponseEntity<CategoryResponseDto>(dto, HttpStatus.OK);
	    }
	}
	
	
	public ResponseEntity<CommonApiResponse>deletebyid(int id){
		
		CommonApiResponse api = new CommonApiResponse();
		if(id==0) {
			api.setResponseMessage("id is null");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		boolean b=cs.delete(id);
		if(b) {
			api.setResponseMessage(id+" id delted successfully");
			api.setSuccess(true);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		}else
			api.setResponseMessage("id not found");
		api.setSuccess(false);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
