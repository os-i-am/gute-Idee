package com.coderscampus.olaf.guteidee.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.olaf.guteidee.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u"
		+ " left join fetch u.authorities"
		+ " where u.username = :username")
	User findByUsername(String username);
	
	@Query("select u from User u"
			+ " left join fetch u.likes"
			+ " where u.id = :userId")
	Optional<User> findUserByIdWithLikes(Long userId);

	@Query("select distinct u from User u"
			+ " left join fetch u.authorities"
			+ " left join fetch u.ideas")
	Set<User> findAllUsersWithRolesAndIdeas();

	@Query("select distinct u from User u"
			+ " left join fetch u.authorities"
			+ " where u.id = :userId")
	User findByIdWithRoles(Long userId);
	
	@Query("select u from User u"
			+ " left join fetch u.ideas"
			+ " left join fetch u.likes"
			+ " left join fetch u.authorities"
			+ " where u.id = :userId")
	User findUserByIdWithIdeasAndLikesAndAuthorities(Long userId);

	User findUserById(Long userId);

}
