package com.ElectronicStore.service;

import com.ElectronicStore.dtos.AddItemToCartRequest;
import com.ElectronicStore.dtos.CartDto;

public interface CartService {

	CartDto addItemToCart(String userId,AddItemToCartRequest request);
	
	void removeItemFromCart(String userId,int cartItem);
	
	void clearCart(String userId);
	
	CartDto getCartByUser(String userId);
}
