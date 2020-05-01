package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface User_RegistryRepository extends CrudRepository <User_Registry, Integer>{
	public Iterable <User_Registry> findAllByUser(User user);
}
