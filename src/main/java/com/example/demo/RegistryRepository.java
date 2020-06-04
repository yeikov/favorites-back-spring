package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<Registry, Long>{
	public List <Registry> findAllByTitleAndMediaAndAuthorAndProductionDate(String title, String media,String author, LocalDate year);
}
