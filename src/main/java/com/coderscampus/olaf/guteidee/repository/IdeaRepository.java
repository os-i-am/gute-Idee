package com.coderscampus.olaf.guteidee.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.olaf.guteidee.domain.Idea;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

	@Query("select distinct i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories"
			+ " order by i.creationDate desc")
	Set<Idea> findAllIdeas();
	
	@Query("select distinct i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories"
			+ " where i.completed = true"
			+ " order by i.creationDate desc")
	Set<Idea> findAllCompletedIdeas();

	@Query("select i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories"
			+ " where i.user.id = :userId"
			+ " order by i.creationDate desc")
	Set<Idea> findAllIdeasByUser(Long userId);
	
	@Query("select i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories"
			+ " where i.user.id = :userId"
			+ " and i.completed = true"
			+ " order by i.creationDate desc")
	Set<Idea> findAllCompletedIdeasByUser(Long userId);

	@Query("select i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories"
			+ " where i.id = :ideaId")
	Idea findIdeaById(Long ideaId);

	@Query("select i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories c"
			+ " where c.id = :catId"
			+ " order by i.creationDate desc")
	Set<Idea> findAllIdeasByCategory(Long catId);

	@Query("select i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes"
			+ " left join fetch i.categories c"
			+ " where i.user.id = :userId"
			+ " and c.id = :catId"
			+ " order by i.creationDate desc")
	Set<Idea> findAllIdeasByUserAndCategory(Long userId, long catId);

	@Query("select i from Idea i"
			+ " left join fetch i.user"
			+ " left join fetch i.likes l"
			+ " left join fetch i.categories"
			+ " where l.primaryKey.user.id = :userId"
			+ " and l.liked = true"
			+ " order by i.creationDate desc")
	Set<Idea> findAllLikedIdeasByUserId(Long userId);
	
}
