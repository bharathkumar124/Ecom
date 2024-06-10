package com.example.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.Dto.CartRequestDto;
import com.example.Dto.CartResponseDto;
import com.example.Dto.CommonApiResponse;
import com.example.Service.CartService;
import com.example.Service.ProductService;
import com.example.Service.UserService;
import com.example.entity.Cart;
import com.example.entity.Customer;
import com.example.entity.Product;


@Service
public class CartResource {
	
	@Autowired
	CartService cs;
	
	@Autowired
	UserService us;
	
	@Autowired
	ProductService ps;
	
	public ResponseEntity<CommonApiResponse> addcart(CartRequestDto dto){
		
		CommonApiResponse api = new CommonApiResponse();
		
		if(dto==null||dto.getProductId()==0||dto.getUserId()==0) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		Customer user = us.findbyid(dto.getUserId());
		
		if(user==null) {
			api.setResponseMessage("User is not found failed to add cart");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
		}
		
		Product product = ps.getproductbyid(dto.getProductId());
		
		if(product==null) {
			api.setResponseMessage("Product is not found failed to add cart");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
		}
		
		if(product.getQuantity()<dto.getQuantity()) {
			api.setResponseMessage(product.getQuantity()+"is availabe");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		
		Cart cart = new Cart();
		
		cart.setUser(user);
		cart.setProduct(product);
		cart.setQuantity(dto.getQuantity());
		LocalDateTime currentTime = LocalDateTime.now();

		// Define the format for the string representation of the time expected by setAddedTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Format the current time as a string
		String currentTimeAsString = currentTime.format(formatter);

		// Set the formatted current time to the 'addedTime' field
		cart.setAddedTime(currentTimeAsString);
		
		Cart addcart = cs.addcart(cart);
		
		if(addcart==null) {
			api.setResponseMessage("failed to add cart");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		api.setResponseMessage(" added cart successfull");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<CommonApiResponse> deletecart(int userid,int carid){
		CommonApiResponse api = new CommonApiResponse();
		
		if(carid==0) {
			api.setResponseMessage("id is missing");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
		}
		
		Customer findbyid = us.findbyid(userid);
		
        
        if(findbyid==null) {
        	api.setResponseMessage("your are unauthorised to access the cart");
        	api.setSuccess(false);
        	return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);

        }
        
        Cart findbyid2 = cs.findbyid(carid);
        
        if(findbyid2==null) {
        	api.setResponseMessage("cart not found");
        	api.setSuccess(false);
        	return new ResponseEntity<CommonApiResponse>(api, HttpStatus.NOT_FOUND);
        }
		
        
        try {
            cs.delete(findbyid2);
        } catch (Exception e) {
        	api.setResponseMessage("failed to delete cart");
        	api.setSuccess(false);
        	return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        api.setResponseMessage("cart updated successfully");
    	api.setSuccess(false);
    	return new ResponseEntity<CommonApiResponse>(api, HttpStatus.OK);
       
	}

	
	public ResponseEntity<CartResponseDto> fetchUserCartDetails(int userId) {

	    CartResponseDto response = new CartResponseDto();

	    // Check if the user exists
	    Customer userOptional = us.findbyid(userId);
	    if (userOptional==null) {
	        response.setResponseMessage("Unauthorized: User not found.");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	    }

	    Customer user = userOptional;

	    // Fetch the carts associated with the user
	    List<Cart> carts = cs.findByUser(user);
	    if (CollectionUtils.isEmpty(carts)) {
	        response.setResponseMessage("No products found in cart.");
	        response.setSuccess(false);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    // Calculate the total cart amount
	    BigDecimal totalAmount = carts.stream()
	            .map(cart -> cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())))
	            .reduce(BigDecimal.ZERO, BigDecimal::add);

	    // Set response details
	    response.setTotalCartAmount(totalAmount);
	    response.setCarts(carts);
	    response.setResponseMessage("User cart fetched successfully.");
	    response.setSuccess(true);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}