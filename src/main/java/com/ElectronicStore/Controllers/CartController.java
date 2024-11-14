package com.ElectronicStore.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElectronicStore.dtos.AddItemToCartRequest;
import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.CartDto;
import com.ElectronicStore.service.CartService;

@RestController
@RequestMapping("/carts")

public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItem(
			@PathVariable String userId,
			@RequestBody AddItemToCartRequest request
			){
		CartDto cartDto = cartService.addItemToCart(userId,request);
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<ApiResponseMessage> removeItemfromcart(@PathVariable String userId,@PathVariable int itemId){

		cartService.removeItemFromCart(userId, itemId);
		ApiResponseMessage response = ApiResponseMessage.builder().message("item is remove!!")
				                        .success(true)
                                         .status(HttpStatus.OK)	
                                         .build();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
//	clear cart
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> clearcart(@PathVariable String userId){
		
		cartService.clearCart(userId);
		ApiResponseMessage response = ApiResponseMessage.builder()
				                       .message("cart  is Blank!!")
				                        .success(true)
                                         .status(HttpStatus.OK)	
                                         .build();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
//	add item to cart
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getcart(@PathVariable String userId){
		
CartDto cartDto = cartService.getCartByUser(userId);		
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
	}
	
	
}
