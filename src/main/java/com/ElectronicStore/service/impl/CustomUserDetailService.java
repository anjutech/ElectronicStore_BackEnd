package com.ElectronicStore.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ElectronicStore.entities.user;
import com.ElectronicStore.repositories.userRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private userRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		user user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found !"));
		return user;
	}
	// we do 3 thing to make user login from direct database
//  first extend userdetails in user table
//  second make a CustomUserDetailService
//  third make a daoauthenticator

}
