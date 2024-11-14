package com.ElectronicStore.service.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.ElectronicStore.Exceptions.BadApiRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ElectronicStore.Exceptions.ResourceNotFoundException;
import com.ElectronicStore.dtos.AddItemToCartRequest;
import com.ElectronicStore.dtos.CartDto;
import com.ElectronicStore.entities.Cart;
import com.ElectronicStore.entities.CartItem;
import com.ElectronicStore.entities.Product;
import com.ElectronicStore.entities.user;
import com.ElectronicStore.repositories.CartItemRepository;
import com.ElectronicStore.repositories.CartRepository;
import com.ElectronicStore.repositories.ProductRepository;
import com.ElectronicStore.repositories.userRepo;
import com.ElectronicStore.service.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private userRepo userRepo;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request)  {
		// TODO Auto-generated method stub
		
		int quantity = request.getQuantity();
		String productId = request.getProductId();
		if (quantity <= 0) {
			throw new BadApiRequest("Requested quantity is not valid !!");
		}
//		fetch the product
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product id not found !!"));
//		fetch the user from db
		user user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("ID NOT FOUND !!"));
		
		Cart cart = null;
		try {
			cart = cartRepository.findByUser(user).get();
			
		}catch (NoSuchElementException e) {
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
		}
		
//		perfoam cart operation
//		if persent cart item:to update kr do
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		List<CartItem> items = cart.getItems();
		items = items.stream().map(item->{
			
			if(item.getProduct().getProductId().equals(productId)) {
				
//				item is persent
				item.setQuantity(quantity);
				item.setTotalPrice(quantity*product.getDiscountedPrice());
				updated.set(true);
			}
			
			return item;
		}).collect(Collectors.toList());
		
//		cart.setItems(updatedItems);
		
//		create items
		if(!updated.get()) {
			CartItem cartItem = CartItem.builder().quantity(quantity)
					.totalPrice(quantity*product.getDiscountedPrice())
					.cart(cart)
					.product(product)
					.build();
			
			cart.getItems().add(cartItem);
		}
		
		cart.setUser(user);
		Cart updateCart= cartRepository.save(cart);
		
		return mapper.map(updateCart,CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItem) {
		// TODO Auto-generated method stub

		CartItem cartItem2=cartItemRepository.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("Id not found"));
		cartItemRepository.delete(cartItem2);
	}

	@Override
	public void clearCart(String userId) {
		// TODO Auto-generated method stub
		user user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Id not found"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Id not found for delete!!"));
	    cart.getItems().clear();
		cartRepository.save(cart);
	}

	@Override
	public CartDto getCartByUser(String userId) {
		// TODO Auto-generated method stub
//		Cart idNotFound = cartRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Id not found"));


		user user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Id not found"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Id not found for delete!!"));

//		return mapper.map(idNotFound,CartDto.class);
		return mapper.map(user,CartDto.class);
}
}
