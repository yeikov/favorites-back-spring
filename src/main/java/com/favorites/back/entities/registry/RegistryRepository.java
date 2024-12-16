package com.favorites.back.entities.registry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistryRepository extends JpaRepository<Registry, Long>{

	
	public Page <Registry> findAllByTitleIgnoreCaseContaining(String title, PageRequest pageRequest);


	public List <Registry> findAllByTitleAndMediaAndAuthorAndProductionDate(String title, String media,String author, LocalDate year);

 	@Query(value = "SELECT r.id, r.title, r.author, r.media, r.production_date, r.favorite_sum, r.mentions, r.recommend_sum, FROM registry AS r WHERE r.media = :media ORDER BY r.favorite_sum DESC LIMIT 8;",
			nativeQuery = true)
	public List <Registry> findTopFavoriteByMedia(String media);

	@Query(value = "SELECT r.id, r.title, r.author, r.media, r.production_date, r.favorite_sum, r.mentions, r.recommend_sum, FROM registry AS r WHERE r.media = :media ORDER BY r.recommend_sum DESC LIMIT 8;",
			nativeQuery = true)
	public List <Registry> findTopRecommendByMedia(String media);
	
}
