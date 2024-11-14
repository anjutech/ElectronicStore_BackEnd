package com.ElectronicStore.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElectronicStore.entities.Categories;
import com.ElectronicStore.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
	Page<Product> findByLiveTrue(Pageable pageable);
	
	 Page<Product> findByCategory(Categories category,Pageable pageable); 
}
