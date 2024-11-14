package com.ElectronicStore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class categoryDto {
	
	private String categoryId;
	@NotBlank
//	@Min(value = 4, message ="title must be 4 character")
	private String title;
	@NotBlank(message = "description should not be empty !")
	private String description;
	private String coverImage;

}
