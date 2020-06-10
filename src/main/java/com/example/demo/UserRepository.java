package com.example.demo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByeMail(String eMail);
	
	@Query(
			  //value = "SELECT * FROM user ORDER by user.name LIMIT 14",
			value = "select * from  user where user.id= any (select distinct user_id from  assessment inner join user on user.id=assessment.user_id )  limit 10",
			  nativeQuery = true)
	public List <User> recent(String criterio);
	
}
