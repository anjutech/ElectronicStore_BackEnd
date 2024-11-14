package com.ElectronicStore.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ElectronicStore.dtos.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

//	handle resource not found Exception
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
		
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.NOT_FOUND);
		
		
	}
	
//	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> MethodArgumentNotValidExceptionHandler( MethodArgumentNotValidException ex){
	
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String,Object> response = new HashMap<>();
		allErrors.stream().forEach(objectError -> {
			String message = objectError.getDefaultMessage();
			String field = ((FieldError)objectError).getField();
			response.put(field, message);
		
		});
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		
	}
	
//	BadApiRequest
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponseMessage> BadApiRequestHandler(BadApiRequest ex){
		
		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
		
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.BAD_REQUEST);
		
		
	}
	
}
