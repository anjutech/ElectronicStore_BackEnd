package com.ElectronicStore.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface fileService {

	String uploadFile(MultipartFile file , String path) throws IOException;
	
	InputStream getResource (String path , String name) throws IOException;
	
}
