package com.example.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.OrderDto;
import com.example.Dto.OrderResponseDto;
import com.example.Service.CartService;
import com.example.Service.CustomerOrderService;

import com.example.Service.DeliverPersonService;
import com.example.Service.DeliveryPersonorderService;
import com.example.Service.OrderService;
import com.example.Service.SellerOrderService;
import com.example.Service.UserService;
import com.example.entity.Cart;
import com.example.entity.Customer;
import com.example.entity.CustomerOrder;
import com.example.entity.DeliveryPerson;
import com.example.entity.DeliveryPersonOrder;
import com.example.entity.Orders;
import com.example.entity.SellerOrders;

@Service
public class OrderResource {

	@Autowired
	UserService us;

	@Autowired
	CartService cs;

	@Autowired
	OrderService os;
	
	@Autowired
	DeliveryPersonorderService dpos;
	
	@Autowired
	DeliverPersonService dps;
	
	@Autowired
	CustomerOrderService cos;
	
	@Autowired
	SellerOrderService sos;

	public ResponseEntity<CommonApiResponse> placeorder(int userid, String deliveryaddress) {

		CommonApiResponse api = new CommonApiResponse();

		if (userid == 0) {
			api.setResponseMessage("user id missing");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
		}
		Customer findbyid = us.findbyid(userid);

		if (findbyid == null) {

			api.setResponseMessage("user is not found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
		}

		List<Cart> cartItems = cs.findByUser(findbyid);
		if (cartItems.isEmpty()) {
			api.setResponseMessage("Cart is empty. No items to order.");
			api.setSuccess(false);
			return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
		}

		// Create a list to hold new orders
		List<Orders> newOrders = new ArrayList<>();

		// Create orders for each item in the cart
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expectedDeliveryDate = now.plusDays(3);
		CustomerOrder customerOrder = new CustomerOrder();
		UUID randomUUID = UUID.randomUUID();
		List<CustomerOrder>listcus=new ArrayList<>();
		List<SellerOrders>listsellers=new ArrayList<>();
		SellerOrders sellerOrders = new SellerOrders();
		
		for (Cart cart : cartItems) {
			Orders order = new Orders();
			order.setOrderId(randomUUID.toString());
			order.setUser(findbyid);
			order.setDeliverAddress(deliveryaddress);
			order.setExpecteddeliveryDate(expectedDeliveryDate.toString());
			order.setProduct(cart.getProduct());
			order.setQuantity(cart.getQuantity());
			order.setOrderTime(now.toString());
			order.setSeller(cart.getProduct().getSeller());
			order.setStatus("Pending"); // Default status for new orders
			// Calculate the total amount based on product quantity
			BigDecimal productPrice = cart.getProduct().getPrice();
			int quantity = cart.getQuantity();
			BigDecimal totalAmount = productPrice.multiply(BigDecimal.valueOf(quantity));

			// Set the calculated total amount to the order
			order.setTotalamount(totalAmount.toString());
			
			customerOrder.setCustomername(findbyid.getFirstName());
			customerOrder.setCustomerid(findbyid.getId());
			customerOrder.setOrderid(randomUUID.toString());
			customerOrder.setDeliveryAddress(deliveryaddress);
			customerOrder.setDeliverystatus("Pending");
			customerOrder.setExpecteddeliverydate(expectedDeliveryDate.toString());
			
			customerOrder.setProductname(cart.getProduct().getName());
			customerOrder.setQuantity(cart.getQuantity());
			customerOrder.setTotalamount(totalAmount.toString());
			listcus.add(customerOrder);
			
			//seller
			sellerOrders.setCustomername(findbyid.getFirstName());
			sellerOrders.setCustomerid(findbyid.getId());
			sellerOrders.setDeliveryAddress(deliveryaddress);
			sellerOrders.setExpecteddeliverydate(expectedDeliveryDate.toString());
			sellerOrders.setDeliverystatus("Pending");
			sellerOrders.setOrderid(randomUUID.toString());
			sellerOrders.setProduct(cart.getProduct());
			sellerOrders.setSellerid(cart.getProduct().getSeller().getId());
			sellerOrders.setQuantity(cart.getQuantity());
			sellerOrders.setTotalamount(totalAmount.toString());
			listsellers.add(sellerOrders);
			// Add to the list of new orders
			newOrders.add(order);
		}

		// Save all orders to the database
		os.addOrder(newOrders);
		cos.savedata(listcus);
sos.saveall(listsellers);
		// Clear the user's cart if needed
		// cs.deleteAll(cartItems);

		// Set response information
		api.setResponseMessage("Order(s) placed successfully.");
		api.setSuccess(true);
		return new ResponseEntity<>(api, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateorder(String orderid, String newaddres) {

		CommonApiResponse api = new CommonApiResponse();
		if (orderid.isEmpty() || newaddres.isEmpty()) {
			api.setResponseMessage("oderid id missing");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
		}

		 List<Orders> fidbyid = os.fidbyid(orderid);
		if (fidbyid.isEmpty()) {
			api.setResponseMessage("no orders found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.NOT_FOUND);
		}
		Orders orders = fidbyid.get(0);
		orders.setDeliverAddress(newaddres);
		Orders updateorder = os.updateorder(orders);
		//custiomerorders
	 CustomerOrder byOrderid = cos.findByOrderid(orderid);
		
		byOrderid.setDeliveryAddress(newaddres);
		CustomerOrder updateaddress = cos.updateaddress(byOrderid);
		//sellerorders
		
SellerOrders findbyorderid = sos.findbyorderid(orderid);
findbyorderid.setDeliveryAddress(newaddres);
List<Orders> fidbyid2 = os.fidbyid(orderid);
Orders orders2 = fidbyid2.get(0);
orders2.setDeliverAddress(newaddres);
os.updateorder(orders2);//orders


SellerOrders updateadd = sos.updateadd(findbyorderid);
		if (updateorder == null) {
			api.setResponseMessage("failed to upate the address ");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		api.setResponseMessage("successfully upated the address ");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api, HttpStatus.OK);
	}
	
	
	public ResponseEntity<OrderResponseDto>findbycustomerid(int customerid){
		
		OrderResponseDto api = new OrderResponseDto();
		
		String status="Pending";
		
		if(customerid==0) {
			api.setResponseMessage("order id missing");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.BAD_REQUEST);
		}
		
		List<CustomerOrder> byCustomerid = cos.findByCustomeridAndDeliverystatus(customerid,status);
		
		if(byCustomerid==null||byCustomerid.isEmpty()) {
			api.setResponseMessage(" no order found ");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.NOT_FOUND);
		}
		 

	       
	       
	        api.setCusprder(byCustomerid);
	        api.setResponseMessage("Order found");
	        api.setSuccess(true);
	        return new ResponseEntity<OrderResponseDto>(api,HttpStatus.OK);
	}
	
	
	
	
	
public ResponseEntity<OrderResponseDto>findbysellerid(int sellerid){
		
		OrderResponseDto api = new OrderResponseDto();
		String status="Pending";
		
		if(sellerid==0) {
			api.setResponseMessage("order id missing");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.BAD_REQUEST);
		}
		
		List<SellerOrders> fetchallbyid = sos.findBySelleridAndOrderid(sellerid,status);
		
		if(fetchallbyid==null||fetchallbyid.isEmpty()) {
			api.setResponseMessage(" no order found ");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.NOT_FOUND);
		}
		
	        api.setListseller(fetchallbyid);
	        api.setResponseMessage("Order found");
	        api.setSuccess(true);
	        return new ResponseEntity<OrderResponseDto>(api,HttpStatus.OK);
	}
	

	public ResponseEntity<OrderResponseDto>fetchall(){
		
		OrderResponseDto api = new OrderResponseDto();
		
		List<CustomerOrder> findall = cos.findall();
		
		if(findall.isEmpty()) {
			api.setResponseMessage("failed to fetch orders");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		api.setCusprder(findall);
		api.setResponseMessage("orders fetched success");
		api.setSuccess(true);
		return new ResponseEntity<OrderResponseDto>(api,HttpStatus.OK);
	}
	
	public ResponseEntity<CommonApiResponse>deliverypersonupdate(String orderid,String deliverystatus){
		
		CommonApiResponse api = new CommonApiResponse();
		if(deliverystatus==null||deliverystatus.isEmpty()||orderid==null||orderid.isEmpty()) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		List<Orders> fidbyid = os.fidbyid(orderid);
		
		if(fidbyid.isEmpty()) {
			api.setResponseMessage("orders not found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		Orders order = fidbyid.get(0);
		LocalDateTime now = LocalDateTime.now();
		
		LocalTime now2 = LocalTime.now();
		order.setDeliveryDate(now.toString());
		order.setDeliveryStatus(deliverystatus);
		order.setDeliveryTime(now2.toString());
		
		Orders updateorder = os.updateorder(order);
		
		if(updateorder==null) {
			api.setResponseMessage("failted to update order status");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		api.setResponseMessage("order status update success fully");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<CommonApiResponse>deleteorder(String orderid){
		
		CommonApiResponse api = new CommonApiResponse();
		
		if(orderid.isEmpty()||orderid==null) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		CustomerOrder byOrderid = cos.findByOrderid(orderid);
		
		if(byOrderid==null) {
			api.setResponseMessage("no orders found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		boolean delete = cos.delete(byOrderid);
		
		boolean deleteorder = os.delete(orderid);
		
		sos.delete(orderid);
		
		if(deleteorder) {
			api.setResponseMessage("order delted successfull");
			api.setSuccess(true);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		}
		api.setResponseMessage("failed to delete order");
		api.setSuccess(false);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		
	}

	public ResponseEntity<CommonApiResponse>assigndeliverperson(String orderid,int deliverpersonid){
		
		CommonApiResponse api = new CommonApiResponse();
		
		if(orderid==null) {
			api.setResponseMessage("orderid is missing");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		SellerOrders findbyorderid = sos.findbyorderid(orderid);
		
		if(findbyorderid==null) {
			api.setResponseMessage("no orders found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
		}
		
		
		Optional<DeliveryPerson> del = dps.findbyid(deliverpersonid);
		DeliveryPerson deliveryPerson = del.get();
		
		if(deliveryPerson==null) {
			api.setResponseMessage("deliveryperson not found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
		}
		
		
		DeliveryPersonOrder deliveryPersonOrder = new DeliveryPersonOrder();
		
		deliveryPersonOrder.setCustomerid(findbyorderid.getCustomerid());
		deliveryPersonOrder.setCustomername(findbyorderid.getCustomername());
		deliveryPersonOrder.setDeliveryAddress(findbyorderid.getDeliveryAddress());
		deliveryPersonOrder.setExpecteddeliverydate(findbyorderid.getExpecteddeliverydate());
		deliveryPersonOrder.setOrderid(findbyorderid.getOrderid());
		deliveryPersonOrder.setProductname(findbyorderid.getProduct().getName());
		deliveryPersonOrder.setQuantity(findbyorderid.getQuantity());
		deliveryPersonOrder.setSellerid(findbyorderid.getSellerid());
		deliveryPersonOrder.setTotalamount(findbyorderid.getTotalamount());
		deliveryPersonOrder.setDeliverpersonid(deliveryPerson.getId());
		deliveryPersonOrder.setDeliverypersonname(deliveryPerson.getFirstName());
		
	
		
		DeliveryPersonOrder adddorder = dpos.adddorder(deliveryPersonOrder);//add data in deliveryperson table
		
		
		SellerOrders sor = sos.findbyorderid(orderid);
		
		sor.setDelivery(deliveryPerson);//assign deliveryperson
		
		SellerOrders updateadd = sos.updateadd(sor);
		
		CustomerOrder co = cos.findByOrderid(orderid);
		co.setDeliverpersonContact(deliveryPerson.getPhoneNo());
		co.setDeliverypersonName(deliveryPerson.getFirstName());
		
		cos.updatedata(co);
		
		List<Orders> fidbyid = os.fidbyid(orderid);
		Orders orders = fidbyid.get(0);
		orders.setDeliveryPerson(deliveryPerson);
		
		if(updateadd==null) {
			api.setResponseMessage("failed to assign deliveryperon");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		api.setResponseMessage("successfully assigned deliveryperson");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
	
	}
	
	
	
	public ResponseEntity<OrderResponseDto>findalldeliverypersonOrders(int id){
		OrderResponseDto api = new OrderResponseDto();
		
		if(id<=0) {
			api.setResponseMessage("invalid id");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.BAD_REQUEST);
		}
		
		List<DeliveryPersonOrder> findbyid = dpos.findByDeliverpersonid(id);
		
		if(findbyid.isEmpty()) {
			api.setResponseMessage("no orders found");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		api.setListdelivery(findbyid);
		api.setResponseMessage("orders found");
		api.setSuccess(true);
		return new ResponseEntity<OrderResponseDto>(api,HttpStatus.OK);
	}
	
	public ResponseEntity<OrderResponseDto>findallOrders(){
		
		OrderResponseDto api = new OrderResponseDto();
		
		List<Orders> fetchall = os.fetchall();
		
		if(fetchall.isEmpty()) {
			api.setResponseMessage("no orders found");
			api.setSuccess(false);
			return new ResponseEntity<OrderResponseDto>(api,HttpStatus.NOT_FOUND);
		}
		OrderDto orderDto = new OrderDto();
		
		List<OrderDto> arrayList = new ArrayList<>();
		
		for(Orders or:fetchall) {
			orderDto.setOrderid(or.getOrderId());
			orderDto.setCustomerid(or.getUser().getId());
			orderDto.setDelipersonid(or.getDeliveryPerson().getId());
			orderDto.setDeliveryaddress(or.getDeliverAddress());
			orderDto.setDeliverydate(or.getDeliveryDate());
			orderDto.setDeliverystatus(or.getStatus());
			orderDto.setProductid(or.getProduct().getId());
			orderDto.setProductname(or.getProduct().getName());
			orderDto.setQuantity(or.getQuantity());
			orderDto.setSellerid(or.getSeller().getId());
			orderDto.setTotalamount(or.getTotalamount());
			arrayList.add(orderDto);
			
			
		}
		
		api.setOrders(arrayList);
		api.setResponseMessage("orders found");
		api.setSuccess(true);
		return new ResponseEntity<OrderResponseDto>(api,HttpStatus.OK);
		
	}
	
	
	
	public ResponseEntity<CommonApiResponse>updatedeliverystatus(String orderid){
		
		CommonApiResponse api = new CommonApiResponse();
		
		if(orderid==null) {
			api.setResponseMessage("Invalid input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		DeliveryPersonOrder dps = dpos.findByOrderid(orderid);
		
		if(dps==null) {
			api.setResponseMessage("no orders found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
		}
		dps.setDeliverystatus("Success");
		dps.setDeliverydate(LocalDateTime.now().toString());
	
		
	CustomerOrder co = cos.findByOrderid(orderid);
	co.setDeliverydate(LocalDateTime.now().toString());
	co.setDeliverystatus("Success");
	
	SellerOrders so = sos.findbyorderid(orderid);
	so.setDeliverydate(LocalDateTime.now().toString());
	so.setDeliverystatus("Success");
	DeliveryPersonOrder update = dpos.update(dps);
	SellerOrders updateadd = sos.updateadd(so);
	CustomerOrder updatedata = cos.updatedata(co);
	
	if(update==null||updateadd==null||updatedata==null) {
		api.setResponseMessage("failed to update");
		api.setSuccess(false);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	api.setResponseMessage("Order deliverd successfully");
	api.setSuccess(true);
	return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
	}
}
