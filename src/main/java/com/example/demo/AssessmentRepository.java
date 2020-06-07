package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository <Assessment, Long>{
	public List <Assessment> findAllByUser(User user);
	
	public List <Assessment> findAllByRegistry(Registry registry);
	
	public Iterable <Assessment> findAllByUserAndRegistry(User user, Registry registry);
	
	public Optional <Assessment> findById(Long id);
	
	public List <Assessment> findAllByRegistryMedia(String media);
}
