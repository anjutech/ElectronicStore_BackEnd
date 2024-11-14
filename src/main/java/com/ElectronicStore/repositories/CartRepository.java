package com.ElectronicStore.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElectronicStore.entities.Cart;
import com.ElectronicStore.entities.user;

@Repository
public interface CartRepository extends JpaRepository<Cart, String>{

	Optional<Cart> findByUser(user user);
}
