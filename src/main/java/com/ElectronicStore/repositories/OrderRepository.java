package com.ElectronicStore.repositories;

import com.ElectronicStore.entities.Order;
import com.ElectronicStore.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUser(user user);
}
    