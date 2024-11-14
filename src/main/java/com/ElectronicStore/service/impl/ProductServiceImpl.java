package com.ElectronicStore.service.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ElectronicStore.Exceptions.ResourceNotFoundException;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.ProductDto;
import com.ElectronicStore.entities.Categories;
import com.ElectronicStore.entities.Product;
import com.ElectronicStore.helpers.Helper;
import com.ElectronicStore.repositories.CategoryRepository;
import com.ElectronicStore.repositories.ProductRepository;
import com.ElectronicStore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepository;

	public ProductDto create(ProductDto productDto) {

		Product product = mapper.map(productDto, Product.class);

		// product id
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		// added
		product.setAddeddate(new Date());
		Product saveProduct = productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		// TODO Auto-generated method stub

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht given ID !!"));
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		product.setProductImageName(productDto.getProductImageName());

		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht given ID !!"));
		productRepository.delete(product);
	}

	public ProductDto get(String productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht given ID !!"));

		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		return Helper.getPegebaleResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByLiveTrue(pageable);
		return Helper.getPegebaleResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
		return Helper.getPegebaleResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
		// TODO Auto-generated method stub

//		fetch category from db

		Categories catogries = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found !!"));
		Product product = mapper.map(productDto, Product.class);

//		product id
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);

//		added
		product.setAddeddate(new Date());
		product.setCategory(catogries);
		Product saveProduct = productRepository.save(product);

		return mapper.map(saveProduct, ProductDto.class);
	}

//	update category of existing record------------------------------------------------------------------------------------------------------------------

	@Override
	public ProductDto updateCategory(String productId, String CategoryId) {
		// TODO Auto-generated method stub

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found With Given Id !!"));
		Categories category = categoryRepository.findById(CategoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found!!"));
		product.setCategory(category);
		Product saveProduct = productRepository.save(product);

		return mapper.map(saveProduct, ProductDto.class);
	}

//	give all product of given if of category-------------------------------------
	@Override
	public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		
		Categories category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found!!"));
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);		
		
		Page<Product> page = productRepository.findByCategory(category,pageable);
		return Helper.getPegebaleResponse(page, ProductDto.class);
	}

	

}
