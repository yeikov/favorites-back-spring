package com.example.demo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface User_RegistryRepository extends CrudRepository <User_Registry, Long>{
	public Iterable <User_Registry> findAllByUser(User user);
	
	public Iterable <User_Registry> findAllByUserAndRegistry(User user, Registry registry);
	
	public Optional<User_Registry> findById(Long id);
}
