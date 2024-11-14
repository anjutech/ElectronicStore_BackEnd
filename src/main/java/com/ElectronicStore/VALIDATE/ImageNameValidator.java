package com.ElectronicStore.VALIDATE;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements  ConstraintValidator<ImageNameValid,String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
//		logic
		
		if (value.isBlank()) {
			return false;
		}else {
			return true;
		}
		
		

	}

}
