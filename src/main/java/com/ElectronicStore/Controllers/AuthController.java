package com.ElectronicStore.Controllers;


import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElectronicStore.Security.JwtHelper;
import com.ElectronicStore.dtos.JwtRequest;
import com.ElectronicStore.dtos.JwtResponse;
import com.ElectronicStore.dtos.userDto;
//import com.ElectronicStore.service.userService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")

public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private ModelMapper ModelMapper;
	
	@Autowired
	private JwtHelper helper;
	
//	@Autowired
//	private userService userService;

//	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception {
		try{

		this.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.helper.generateToken(userDetails);

		userDto userDto = ModelMapper.map(userDetails,userDto.class);
		
		JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}catch (Exception e){
			throw new Exception(e.getMessage());

		}
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}


	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}

	
	@GetMapping("/current")
	public ResponseEntity<userDto> getcurrentUser(Principal principal) {
		String name = principal.getName();
        return new ResponseEntity<>(ModelMapper.map(userDetailsService.loadUserByUsername(name), userDto.class), HttpStatus.OK);
	}
	
	
}
