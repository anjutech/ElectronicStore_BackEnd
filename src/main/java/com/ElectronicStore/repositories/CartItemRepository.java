package com.ElectronicStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElectronicStore.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
