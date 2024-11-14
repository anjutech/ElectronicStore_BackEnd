package com.ElectronicStore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElectronicStore.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,String>{

}
