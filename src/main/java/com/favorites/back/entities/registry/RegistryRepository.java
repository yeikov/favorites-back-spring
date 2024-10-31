package com.favorites.back.entities.registry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistryRepository extends JpaRepository<Registry, Long>{

	
	public List <Registry> findAllByTitleIgnoreCaseContaining(String title);


	public List <Registry> findAllByTitleAndMediaAndAuthorAndProductionDate(String title, String media,String author, LocalDate year);

 	@Query(value = "SELECT DISTINCT id, title, author, media, production_date FROM (SELECT r.id, r.title, r.author, r.media, r.production_date, a.registry_id, a.favorite FROM registry AS r INNER JOIN assessment AS a ON r.id = a.registry_id AND r.media = :media GROUP BY r.title, a.id ORDER BY a.favorite DESC) AS dt LIMIT 8;",
			nativeQuery = true)
	public List <Registry> findTopFavoriteByMedia(String media);
	
	@Query(value = "SELECT DISTINCT id, title, author, media, production_date FROM (SELECT r.id, r.title, r.author, r.media, r.production_date, a.registry_id, a.recommend FROM registry AS r INNER JOIN assessment AS a ON r.id = a.registry_id AND r.media = :media GROUP BY r.title, a.id ORDER BY a.recommend DESC) AS dt LIMIT 8;",
			nativeQuery = true)
	public List <Registry> findTopRecommendByMedia(String media);
	
}
