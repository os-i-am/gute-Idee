package com.coderscampus.olaf.guteidee.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class LikesId implements Serializable {

	private static final long serialVersionUID = 2465005064642281257L;
	private User user;
	private Idea idea;
	
	public LikesId() {}

	public LikesId(User user, Idea idea) {
		this.user = user;
		this.idea = idea;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST})
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST})
	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idea == null) ? 0 : idea.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		LikesId other = (LikesId) obj;
		if (idea == null) {
			if (other.idea != null)
				return false;
		} else if (!idea.equals(other.idea))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}