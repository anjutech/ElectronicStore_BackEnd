package com.ElectronicStore.service;

import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.categoryDto;

public interface categoryService {

	categoryDto create(categoryDto categoryDto);

	categoryDto update(categoryDto categoryDto, String categoryId);

	void delete(String categoryId);

	categoryDto get(String categoryId);

	PageableResponse<categoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

}
