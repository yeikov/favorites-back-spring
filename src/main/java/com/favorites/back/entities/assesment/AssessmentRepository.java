package com.favorites.back.entities.assesment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.user.User;

public interface AssessmentRepository extends JpaRepository <Assessment, Long>{
	
	public List <Assessment> findAllByUser(User user);
	
	public List <Assessment> findAllByRegistry(Registry registry);
	
	public Iterable <Assessment> findAllByUserAndRegistry(User user, Registry registry);
	
	@SuppressWarnings("null")
	public Optional <Assessment> findById(Long id);
	
	public List <Assessment> findAllByRegistryMedia(String media);
	
	@Query(
			value = "SELECT a.* FROM assessment AS a INNER JOIN registry AS r ON a.registry_id=r.id AND r.media=:media ORDER BY a.favorite DESC LIMIT 8;",
			nativeQuery = true
			)
	public List <Assessment> findM(String media);
	
	
	@Query(
			value = "SELECT a.* FROM assessment AS a INNER JOIN registry AS r ON a.registry_id=r.id AND a.user_id=:userId AND r.media=:media ORDER BY a.favorite DESC;", 
			nativeQuery = true)
	public List <Assessment> findUM(Long userId, String media);
}
//"SELECT a.*, r.media, r.author, r.title, r.id as od FROM assessment AS a INNER JOIN registry AS r ON a.registry_id=r.id AND a.user_id=:userId AND r.media=':media' ORDER BY a.favorite DESC;", 