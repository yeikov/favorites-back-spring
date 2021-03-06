package com.example.demo.entities.registry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistryRepository extends JpaRepository<Registry, Long>{
	public List <Registry> findAllByTitleAndMediaAndAuthorAndProductionDate(String title, String media,String author, LocalDate year);
	
	@Query(value = "SELECT DISTINCT *, COUNT(*) FROM REGISTRY AS r INNER JOIN ASSESSMENT AS a ON r.id = a.registry_id and r.media = :media  GROUP BY r.title ORDER BY a.favorite*COUNT(*) DESC LIMIT 8;",
			nativeQuery = true)
	public List <Registry> findTopFavoriteByMedia(String media);
	
	@Query(value = "SELECT DISTINCT *, COUNT(*) FROM REGISTRY AS r INNER JOIN ASSESSMENT AS a ON r.id = a.registry_id and r.media = :media  GROUP BY r.title ORDER BY a.recommend*COUNT(*) DESC LIMIT 8;",
			nativeQuery = true)
	public List <Registry> findTopRecommendByMedia(String media);
	
}
