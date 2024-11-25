package com.favorites.back.entities.user;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByeMail(String eMail);
	
	@Query(
			value = "SELECT u FROM user as us LIMIT 10;",
			nativeQuery = true)
	public List <User> recent(String criterio);
	
}
