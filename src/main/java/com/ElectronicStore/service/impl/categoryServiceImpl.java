package com.ElectronicStore.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.ElectronicStore.Exceptions.ResourceNotFoundException;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.categoryDto;
import com.ElectronicStore.entities.Categories;
import com.ElectronicStore.helpers.Helper;
import com.ElectronicStore.repositories.CategoryRepository;
import com.ElectronicStore.service.categoryService;

@Service
public class categoryServiceImpl implements categoryService {

	 
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	
	
	@Override
	public categoryDto create(categoryDto categoryDto) {
		
		Categories  category = mapper.map(categoryDto,Categories.class);
		Categories savedCategories = categoryRepository.save(category);
		return mapper.map(savedCategories,categoryDto.class);
	}

	@Override
	public categoryDto update(categoryDto categoryDto, String categoryId) {

		//		get category of given id
		
		Categories category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptions"));
		
//		update category details
		
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());
		
		Categories updatedCategories = categoryRepository.save(category);
		
		return mapper.map(updatedCategories,categoryDto.class);
	}

	@Override
	public void delete(String categoryId) {
		// TODO Auto-generated method stub
		Categories category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptions"));
         categoryRepository.delete(category);
		
	}

	@Override
	public PageableResponse<categoryDto> getAll(int pageNumber,int pageSize , String sortBy,String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Categories> page = categoryRepository.findAll(pageable);
		PageableResponse<categoryDto> pageableResponse =  Helper.getPegebaleResponse(page, categoryDto.class);
		
		return pageableResponse;
	}

	@Override
	public categoryDto get(String categoryId) {
		// TODO Auto-generated method stub
		Categories category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptions"));

		return mapper.map(category,categoryDto.class);
	}

	
	

}
