package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface User_RegistryRepository extends JpaRepository <User_Registry, Long>{
	public List <User_Registry> findAllByUser(User user);
	
	public Iterable <User_Registry> findAllByUserAndRegistry(User user, Registry registry);
	
	public Optional<User_Registry> findById(Long id);
}
