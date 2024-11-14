package com.ElectronicStore.dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class 	CartItemDto {
	private int cartItemId;

	private ProductDto product;

	private int quantity;

	private int totalPrice;

}
