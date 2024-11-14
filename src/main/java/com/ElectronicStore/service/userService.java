package com.ElectronicStore.service;

import java.io.IOException;
import java.util.List;

import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.userDto;

public interface userService {

	userDto createUser(userDto userDto);
	
	userDto updateUser(userDto userDto , String userId);
	
	void deleteUser(String userId) throws IOException;
	
	PageableResponse<userDto> getAllUser(int pageNumber, int pageSize,String sortBy,String sortDir);
	
	userDto getUserById(String userId);
	
	userDto getUserByEmail(String email);
	
	List<userDto>  searchUser (String keyword);
}
