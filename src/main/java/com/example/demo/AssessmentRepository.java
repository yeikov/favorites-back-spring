package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AssessmentRepository extends JpaRepository <Assessment, Long>{
	
	public List <Assessment> findAllByUser(User user);
	
	public List <Assessment> findAllByRegistry(Registry registry);
	
	public Iterable <Assessment> findAllByUserAndRegistry(User user, Registry registry);
	
	public Optional <Assessment> findById(Long id);
	
	public List <Assessment> findAllByRegistryMedia(String media);
	
	@Query(
			value = "SELECT * FROM ASSESSMENT a INNER JOIN REGISTRY r ON a.registry_id=r.id AND r.media=:media LIMIT 8", 
			nativeQuery = true
			)
	public List <Assessment> findM(String media);
	
	
	/*eliminar?*/
	@Query(
			value = "SELECT * FROM ASSESSMENT a INNER JOIN REGISTRY r ON a.registry_id=r.id AND a.user_id=:userId AND r.media=:media", 
			nativeQuery = true
			)
	public List <Assessment> findUM(Long userId, String media);
}
