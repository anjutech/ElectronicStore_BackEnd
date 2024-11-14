package com.ElectronicStore.entities;


import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class user implements UserDetails{

	
	private static final long serialVersionUID = 1L;
	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)//............for autoIncrement
	private String userId;
	private String name;
	@Column(name="email",unique = true)
	private String email;
	private String password;
	private String gender;
	@Column(length = 1000)
	private String about;
	@Column(name = "user_image_name")
	private String userImage;
//
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE	)
	private List<Order> orders = new ArrayList<>();


	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne (mappedBy = "user",cascade = CascadeType.REMOVE)
	private Cart cart;
	
	
	// we do 3 thing to make user login from direct database
//  first extend userdetails in user table
//  second make a CustomUserDetailService
//  third make a daoauthenticator
	
//	must have to be implemented
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
	Set<SimpleGrantedAuthority> authorities =	this.roles.stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		return authorities;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
