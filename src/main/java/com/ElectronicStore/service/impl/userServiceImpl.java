package com.ElectronicStore.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ElectronicStore.entities.*;
import com.ElectronicStore.helpers.Helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ElectronicStore.Exceptions.ResourceNotFoundException;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.dtos.userDto;
import com.ElectronicStore.repositories.RoleRepository;
import com.ElectronicStore.repositories.userRepo;
import com.ElectronicStore.service.userService;


@Service
public class userServiceImpl implements userService{

	@Autowired
	private userRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${normal.role.id}")
	private String normalRoleId;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public userDto createUser(userDto userDto) {
		// TODO Auto-generated method stub
//		generte unique id in string format
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
//		set encode password
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		user user = dtoToEntity(userDto);
		
//		Role of normal user and set it to user
		 Role role = roleRepository.findById(normalRoleId).get();
		 user.getRoles().add(role);
		 
		user savedUser = userRepo.save(user);
		
		userDto newDto = entityToDto(savedUser);
		return newDto;
	}

	

	@Override
	public userDto updateUser(userDto userDto, String userId) {
		// TODO Auto-generated method stub
		
		user user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(" User not Found !"));
		
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		if(!userDto.getPassword().equalsIgnoreCase(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
		user.setUserImage(userDto.getUserImage());
		
//		save data
		
		user updatedUser = userRepo.save(user);
		
		userDto updatedDto = entityToDto(updatedUser);
		
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) throws IOException {
		// TODO Auto-generated method stub
		
		user user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(" User not Found !"));
//		deleter image of user
//		images/user/abc.png
		String fullPath = imagePath + user.getUserImage();
		
		Path path = Paths.get(fullPath);
		Files.delete(path);
		
//		delete user
		userRepo.delete(user); 
		
	}

	@Override
	public PageableResponse<userDto> getAllUser(int pageNumber, int pageSize ,String sortBy , String sortDir) {
		// TODO Auto-generated method stub
//		pageNumber default start from 0
		
		Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<user> page = userRepo.findAll(pageable);
		
	    PageableResponse<userDto> response = 	Helper.getPegebaleResponse(page,userDto.class);
	    
		return response ;
	}

	@Override
	public userDto getUserById(String userId) {
		// TODO Auto-generated method stub
		user user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(" User not Found !"));

		
		
		return entityToDto(user);
	}

	@Override
	public userDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		user user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException(" No User Found "));
		
		
		return entityToDto(user);
	}

	@Override
	public List<userDto> searchUser(String keyword) {
		// TODO Auto-generated method stub
		List<user> users = userRepo.findByNameContaining(keyword);
	    List<userDto> dtoList = 	users.stream().map(user-> entityToDto(user)).collect(Collectors.toList());		
		
		return dtoList;
	}
	
	private user dtoToEntity(com.ElectronicStore.dtos.userDto userDto) {
		
//		user user = user.builder()
//				    .userId(userDto.getUserId())
//		            .name(userDto.getName())
//		            .email(userDto.getEmail())
//		            .password(userDto.getPassword())
//		            .about(userDto.getPassword())
//		            .gender(userDto.getGender())
//		            .imageName(userDto.getUserImage())
//		            .build();
//		user.class is our destination-----------------------------------
		return mapper.map(userDto, user.class);
	}
	
	private userDto entityToDto(user savedUser) {
		// TODO Auto-generated method stub
		
//		userDto userDto = userDto.builder()
//				.userId(savedUser.getUserId())
//	            .name(savedUser.getName())
//	            .email(savedUser.getEmail())
//	            .password(savedUser.getPassword())
//	            .about(savedUser.getPassword())
//	            .gender(savedUser.getGender())
//	            .imageName(savedUser.getUserImage())
//	            .build();
//	
		
		return mapper.map(savedUser, userDto.class);
	}

}
