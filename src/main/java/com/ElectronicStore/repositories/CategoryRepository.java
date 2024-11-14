package com.ElectronicStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElectronicStore.entities.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories,String>{

}
