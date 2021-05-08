package com.coderscampus.olaf.guteidee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.olaf.guteidee.domain.Like;
import com.coderscampus.olaf.guteidee.domain.LikesId;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikesId> {
	
	@Query("select l from Like l"
			+ " left join fetch l.primaryKey p"
			+ " left join fetch p.user u"
			+ " left join fetch p.idea i"
			+ " where u.id = :userId"
			+ " and i.id = :ideaId")
	Like findLikeByUserIdAndIdeaId(Long userId, Long ideaId);

}
