package com.ElectronicStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ElectronicStore.entities.Role;
import com.ElectronicStore.repositories.RoleRepository;

@SpringBootApplication
@EnableWebMvc
@RequestMapping
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);

		System.out.print("Hello World !");
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository repository;

	@Value("${admin.role.id}")
	public String role_admin_id;
	
	@Value("${normal.role.id}")
     public String role_normal_id;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(passwordEncoder.encode("anju9718"));
		try {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------			
//			we can also write normal and admin id configuration in application.properties file as we do for sec erat code in jwtSecurity authentication---------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------         
//			String role_admin_id ="AdminID1234";
//            String role_normal_id ="NormalId56789";
            Role role_admin = Role.builder().roleId(role_admin_id).roleName("ADMIN").build();
            Role role_normal = Role.builder().roleId(role_normal_id).roleName("NORMAL").build();
            repository.save(role_admin);
            repository.save(role_normal);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	}

