package com.coderscampus.olaf.guteidee.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "likes")
@AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id"))
@AssociationOverride(name = "primaryKey.idea", joinColumns = @JoinColumn(name = "idea_id"))
public class Like {

	private LikesId primaryKey = new LikesId();
	private Boolean liked;

	public Like() {
	}

	public Like(Boolean liked) {
		this.liked = liked;
	}

	public Like(LikesId primaryKey, Boolean liked) {
		this.primaryKey = primaryKey;
		this.liked = liked;
	}

	@EmbeddedId
	public LikesId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(LikesId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Transient
	public User getUser() {
		return getPrimaryKey().getUser();
	}

	public void setUser(User user) {
		getPrimaryKey().setUser(user);
	}

	@Transient
	public Idea getIdea() {
		return getPrimaryKey().getIdea();
	}

	public void setIdea(Idea idea) {
		getPrimaryKey().setIdea(idea);
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((liked == null) ? 0 : liked.hashCode());
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Like other = (Like) obj;
		if (liked == null) {
			if (other.liked != null)
				return false;
		} else if (!liked.equals(other.liked))
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}

}
