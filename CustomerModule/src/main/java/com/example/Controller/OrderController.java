package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.OrderResponseDto;
import com.example.Resource.OrderResource;


@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderResource resource;
	
	
@PostMapping(value = "/placeorder")
	public ResponseEntity<CommonApiResponse> oderproduct(@RequestParam int userid ,@RequestParam String deliveraddress){
		return this.resource.placeorder(userid,deliveraddress);
	}

@PutMapping(value = "/updatedeliveryaddress")
public ResponseEntity<CommonApiResponse>updaterderaddress(@RequestParam String orderid,String newaddress){
	return resource.updateorder(orderid, newaddress);
	
}

@GetMapping(value = "/findbycustomerid")
public ResponseEntity<OrderResponseDto>findbyorderid(@RequestParam int customerid){
	return resource.findbycustomerid(customerid);
}

@GetMapping(value = "/fetchallcustomerorders")
public ResponseEntity<OrderResponseDto>fetchall(){
	return resource.fetchall();
}


@GetMapping(value = "/findbysellerrid")
public ResponseEntity<OrderResponseDto>findsellerid(@RequestParam int sellerid){
	return resource.findbysellerid(sellerid);
}



@DeleteMapping(value = "delete/order")
public ResponseEntity<CommonApiResponse>deletebyid(@RequestParam String orderid){
	return resource.deleteorder(orderid);
}
@PostMapping(value = "/assigndeliveryperson")

public ResponseEntity<CommonApiResponse> assigndeliveryperonforOrder(@RequestParam String orderid,@RequestParam int deliverpersonid){
	return resource.assigndeliverperson(orderid, deliverpersonid);
}


@GetMapping(value = "/finddeliverypersonorders")
public ResponseEntity<OrderResponseDto>findbydeliveryid(@RequestParam int id){
	return resource.findalldeliverypersonOrders(id);
}

@GetMapping(value = "/allorders")
public ResponseEntity<OrderResponseDto>findallorders(){
	return resource.findallOrders();
}

@PutMapping(value = "/deliverystatusupdate")
public ResponseEntity<CommonApiResponse>deliveryupdate(@RequestParam String orderid){
	return resource.updatedeliverystatus(orderid);
}

}
