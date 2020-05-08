package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RegistryRepository extends CrudRepository<Registry, Long>{
	public List <Registry> findAllByTitleAndMediaAndAutorAndProductionDate(String title, String media,String autor, LocalDateTime year);
}
