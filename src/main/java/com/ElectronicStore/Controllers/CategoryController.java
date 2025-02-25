package com.ElectronicStore.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;
import com.ElectronicStore.dtos.categoryDto;
import com.ElectronicStore.service.ProductService;
import com.ElectronicStore.service.categoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")

public class CategoryController {
	
	@Autowired
	private categoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<categoryDto> createCategory(@Valid @RequestBody categoryDto categoryDto){
	
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		
		categoryDto categoryDto2 = categoryService.create(categoryDto);
		
		return new ResponseEntity<>(categoryDto2,HttpStatus.CREATED);		
	}
	
//	update-------------------------------------------------------------------------------------------------
	@PutMapping("/{categoryId}")
	public ResponseEntity<categoryDto> updateCategory(
			@PathVariable String categoryId,
			@RequestBody categoryDto categoryDto){
		
	categoryDto updateCategoryDto =	categoryService.update(categoryDto, categoryId);
		
		
		return new ResponseEntity<>(updateCategoryDto,HttpStatus.OK);
	}

//	delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deletedCategory(
			@PathVariable String categoryId){
		
		categoryService.delete(categoryId);
		ApiResponseMessage ResponseMessage = ApiResponseMessage.builder().message("Category Id is deleted !").status(HttpStatus.OK).success(true).build();
		
		return new ResponseEntity<>(ResponseMessage,HttpStatus.OK);
	}
	
//	get all
	@GetMapping
	public ResponseEntity<PageableResponse<categoryDto>> getAll(
		     @RequestParam (value = "pageNumber" , defaultValue = "0",required = false)	int pageNumber,
		     @RequestParam (value = "pageSize" , defaultValue = "10",required = false)	int pageSize,
		     @RequestParam (value = "sortBy" , defaultValue = "title",required = false)	String sortBy,
		     @RequestParam (value = "sortDir" , defaultValue = "asc",required = false)	String sortDir
			){
		
		PageableResponse<categoryDto>  pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	}
	
//	getone
	@GetMapping("/{categoryId}")
	public ResponseEntity<categoryDto> getsingle(
			@PathVariable String categoryId
			){
		categoryDto categoryDto = categoryService.get(categoryId);
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
//	create product with categories
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(
			@PathVariable ("categoryId") String categoryId,
			@RequestBody ProductDto dto){
		
	ProductDto productWithCategory =	productService.createWithCategory(dto, categoryId);
		return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
	}
//	update category of existing product
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> updateCategoryofProduct(
		  @PathVariable String categoryId,
		  @PathVariable String productId
			){
		ProductDto productDto = productService.updateCategory(productId, categoryId);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
//	get products of category 
	  
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
			@PathVariable String categoryId,
		     @RequestParam (value = "pageNumber" , defaultValue = "0",required = false)	int pageNumber,
		     @RequestParam (value = "pageSize" , defaultValue = "10",required = false)	int pageSize,
		     @RequestParam (value = "sortBy" , defaultValue = "title",required = false)	String sortBy,
		     @RequestParam (value = "sortDir" , defaultValue = "asc",required = false)	String sortDir
			){
		
		PageableResponse<ProductDto>  pageableResponse = productService.getAllOfCategory( categoryId,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	}
	
	
	
}
