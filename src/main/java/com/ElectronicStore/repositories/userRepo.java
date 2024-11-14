package com.ElectronicStore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.ElectronicStore.entities.user;

@EnableJpaRepositories
public interface userRepo extends JpaRepository<user, String>{

//	custom method :- iski implementation apneap se run time p program ko mil jaegi spring boot dega
//	findby____field ka nam
	
//	find by yani where ki query lgaega or field jo hmne diya hai usko pass krega
//	user findByEmail(String email);
	
//	agar janna hai ki value hai ya nahi hai with above same condition
Optional<user> findByEmail(String email);

	
//	where ki query lgegi lekin and k sath
//	user findByEmailAndPassword (String email,String password);

//	like ki query bnaega search k liye
	List<user> findByNameContaining(String keyword);
	
}
