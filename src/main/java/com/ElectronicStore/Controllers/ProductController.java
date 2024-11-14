package com.ElectronicStore.Controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.ImageResponseMessage;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;
import com.ElectronicStore.service.ProductService;
import com.ElectronicStore.service.fileService;

import jakarta.servlet.http.HttpServletResponse;


@RequestMapping("/products")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private fileService fileService;
	
	@Value("${product.image.path}")
	private String imagePath;
	
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody
			ProductDto productDto){
		ProductDto createDto = productService.create(productDto);
		return new ResponseEntity<>(createDto,HttpStatus.CREATED);
	}
	
//	update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct( 
			@PathVariable String productId,
			@RequestBody ProductDto productDto 
			){
		ProductDto updatedDto = productService.update(productDto,productId);
		return new ResponseEntity<>(updatedDto,HttpStatus.CREATED);
	}
//	delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId){
		
	  productService.delete(productId);
	  ApiResponseMessage apiResponseMessage =  ApiResponseMessage.builder().message("product is deleted").status(HttpStatus.OK).success(true).build();
	  return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
	}
//	getSingle
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(
			@PathVariable String productId){
		ProductDto getDto = productService.get(productId);
		return new ResponseEntity<>(getDto,HttpStatus.OK);
	}
//	getAll
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAll(
			@RequestParam (value = "pageNumber" , defaultValue = "0",required = false)	int pageNumber,
		     @RequestParam (value = "pageSize" , defaultValue = "10",required = false)	int pageSize,
		     @RequestParam (value = "sortBy" , defaultValue = "title",required = false)	String sortBy,
		     @RequestParam (value = "sortDir" , defaultValue = "asc",required = false)	String sortDir
			){
		  PageableResponse<ProductDto> pageableResponse =  productService.getAll(pageNumber,pageSize,sortBy,sortDir);
		  return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	}

//	/products
//	/products/live
//	/products?live=true
	
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			@RequestParam (value = "pageNumber" , defaultValue = "0",required = false)	int pageNumber,
		     @RequestParam (value = "pageSize" , defaultValue = "10",required = false)	int pageSize,
		     @RequestParam (value = "sortBy" , defaultValue = "title",required = false)	String sortBy,
		     @RequestParam (value = "sortDir" , defaultValue = "asc",required = false)	String sortDir
			){
		  PageableResponse<ProductDto> pageableResponse =  productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
		  return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	}
	
	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> searchEntity(
			@PathVariable String query,
			@RequestParam (value = "pageNumber" , defaultValue = "0",required = false)	int pageNumber,
		     @RequestParam (value = "pageSize" , defaultValue = "10",required = false)	int pageSize,
		     @RequestParam (value = "sortBy" , defaultValue = "title",required = false)	String sortBy,
		     @RequestParam (value = "sortDir" , defaultValue = "asc",required = false)	String sortDir
			){
		  PageableResponse<ProductDto> pageableResponse =  productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
		  return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
	}
	
//	upload  image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponseMessage> uploadProductImage(
			@PathVariable String productId,
			@RequestParam("productImage")MultipartFile image
			) throws IOException{
		String filename = fileService.uploadFile(image,imagePath);
		ProductDto productDto = productService.get(productId);
		productDto.setProductImageName(filename);
		ProductDto updateProduct = productService.update(productDto,productId);
		
		ImageResponseMessage re = ImageResponseMessage.builder().imageName(updateProduct.getProductImageName()).message("Image Uploaded").status(HttpStatus.CREATED).success(true).build();
		
		return new ResponseEntity<>(re,HttpStatus.CREATED);
	}
//	serve user image
	@GetMapping("/image/{productId}")
	public void serveUserImage(@PathVariable String productId,
			HttpServletResponse response) throws IOException {
		
		ProductDto productDto = productService.get(productId);
		
		InputStream resource = fileService.getResource(imagePath,productDto.getProductImageName());
		
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
		
	}
	
	
}
