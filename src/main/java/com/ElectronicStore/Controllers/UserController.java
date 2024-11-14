package com.ElectronicStore.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import com.ElectronicStore.dtos.userDto;
import com.ElectronicStore.service.fileService;
import com.ElectronicStore.service.userService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {

	@Autowired
	private userService userService;

	@Autowired
	private fileService fileService;

	@Value("${user.profile.image.path}")
	private String imageUploadPath;

	@PostMapping("/create")
	public ResponseEntity<userDto> createUser(@Valid @RequestBody userDto userDto) throws InterruptedException {
//        Thread.sleep(2000);
		userDto userDto2 = userService.createUser(userDto);
		return new ResponseEntity<>(userDto2, HttpStatus.CREATED);

	}

	@PutMapping("/{userId}")
	public ResponseEntity<userDto> updateUser(@PathVariable String userId, @Valid @RequestBody userDto userDto) {

		userDto updateUserDto = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) throws IOException {
		userService.deleteUser(userId);
		ApiResponseMessage message = ApiResponseMessage.builder().message("User is Deleted Successfully !!")
				.success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@GetMapping("/get_all")
	public ResponseEntity<PageableResponse<userDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

	) {
		return new ResponseEntity<PageableResponse<userDto>>(
				userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<userDto> getUser(@PathVariable String userId) {
		return new ResponseEntity<userDto>(userService.getUserById(userId), HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<userDto> getUserBYEmail(@PathVariable String email) {
		return new ResponseEntity<userDto>(userService.getUserByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<userDto>> searchUser(@PathVariable String keyword) {
		return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);
	}

//	 for image upload
	@PostMapping("/image/{userId}")
	 public ResponseEntity<ImageResponseMessage> uploadUserImage(
			 @PathVariable String userId,
			 @RequestParam("userImage") MultipartFile image) throws IOException{
		
		String imageName = fileService.uploadFile(image,imageUploadPath);

		
		 userDto user = userService.getUserById(userId);
		 user.setUserImage(imageName);
		 userDto userDto = userService.updateUser(user, userId);
		 
		 
		 
//		 ImageResponseMessage imageResponse= ImageResponseMessage.builder().message("Image Uploaded SuccessFully !! ").imageName(imageName).success(true).status(HttpStatus.CREATED).build();
		 ImageResponseMessage imageResponse= ImageResponseMessage.builder().message("Image Uploaded SuccessFully !! ").imageName(userDto.getUserImage()).success(true).status(HttpStatus.CREATED).build();

		 return new ResponseEntity<ImageResponseMessage>(imageResponse,HttpStatus.CREATED);
		 
	 }
	
//	serve user image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId,
			HttpServletResponse response) throws IOException {
		
		userDto user = userService.getUserById(userId);
		InputStream resource = fileService.getResource(imageUploadPath,user.getUserImage());
		
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
		
	}

}
