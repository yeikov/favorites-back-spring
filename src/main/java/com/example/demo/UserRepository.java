package com.example.demo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
	public Optional <User> findByName(String name);
	public Optional <User> findById(Long id);

}
