package com.favorites.back.entities.viewer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.favorites.back.Media;

public interface ViewerRepository extends JpaRepository<Viewer, Long> {

	default List<Viewer> findAll(){
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		//String owner = authentication.getName();
		//return findByOwner(owner);
		return findAll();
	}

	public Optional<Viewer> findByeMail(String eMail);
	
	@Query(value = "SELECT DISTINCT name, city FROM (SELECT a.viewer FROM assessment AS a WHERE a.media = :media INNER JOIN viewer AS v ON v.id = a.viewer_id) AS dt LIMIT 10;",
			nativeQuery = true)
	public List<Viewer> recent(Media media);
	
}
