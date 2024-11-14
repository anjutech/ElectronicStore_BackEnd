package com.ElectronicStore.service;



import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;


public interface ProductService {

	  ProductDto create(ProductDto productDto);
	  
	  ProductDto update(ProductDto productDto,String productId);
	  
	  void delete(String productId);
	  
	  ProductDto get(String productId);
	  
	  PageableResponse<ProductDto> getAll(int pageNumber, int pageSize,String sortBy,String sortDir);
	  
	  PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize,String sortBy,String sortDir);
	  
	  PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize,String sortBy,String sortDir);
	
	  ProductDto createWithCategory(ProductDto productDto,String categoryId);
	  
	  ProductDto updateCategory(String productId, String CategoryId);
	  
	  PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize,String sortBy,String sortDir);
}
