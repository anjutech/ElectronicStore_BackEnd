package com.ElectronicStore.service.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ElectronicStore.Exceptions.BadApiRequest;
import com.ElectronicStore.service.fileService;



@Service
public class fileServiceimpl implements fileService{

	Logger logger = LoggerFactory.getLogger(fileServiceimpl.class);
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		// TODO Auto-generated method stub
		
		String originalFilename = file.getOriginalFilename();
		logger.info("File name : {}" ,originalFilename);
		String filename = UUID.randomUUID().toString();
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileNameWithExtension = filename + extension;
		String fullPathWithFileName = path + fileNameWithExtension;
		
		if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
			
//			 save file
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			 Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));
			return fileNameWithExtension;
			
			
		} else {
           throw new BadApiRequest("File with this " + extension + "not allowed!");
		}
		
	}

	@Override
	public InputStream getResource(String path, String name) throws IOException {
		// TODO Auto-generated method stub
		String fullPath =   path + File.separator + name;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
		
	}

}
