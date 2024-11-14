package com.ElectronicStore.dtos;

import java.util.HashSet;
import java.util.Set;

//import com.ElectronicStore.VALIDATE.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class userDto {
	
	private String userId;
	
	@Size (min=2,max=20,message = "Invalid Name !")
	private String name;
	
	@Email(message = "InValid User Email ! ")
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$",
            message = "password must be min 4 and max 12 length containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String password;
	
	@NotBlank(message = "Gender is required")
	private String gender;
	private String about;
	
//	@ImageNameValid(message = "custom Validator! ")
	private String userImage;
//	
	private Set<RoleDto> roles = new HashSet<>();
}
