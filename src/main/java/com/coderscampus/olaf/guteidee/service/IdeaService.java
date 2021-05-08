package com.coderscampus.olaf.guteidee.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.olaf.guteidee.domain.Idea;
import com.coderscampus.olaf.guteidee.domain.User;
import com.coderscampus.olaf.guteidee.repository.IdeaRepository;

@Service
public class IdeaService {

	@Autowired
	private IdeaRepository ideaRepo;
	@Autowired
	private CategoryService categoryService;

	public Set<Idea> getAllIdeas() {

		return ideaRepo.findAllIdeas();
	}

	public Idea CreateEmptyIdeaWithCategories() {
		Idea idea = new Idea();
		idea.setCategories(categoryService.getAllCategories());
		return idea;
	}

	public Idea saveNewIdea(User user, Idea idea) {
		idea.setCompleted(false);
		idea.setUser(user);
		return ideaRepo.save(idea);

	}

	public Set<Idea> getUserIdeas(User user, String filter) {
		if (filter == null || filter.isEmpty() || filter.contentEquals("all"))
			return ideaRepo.findAllIdeasByUser(user.getId());
		else if (filter.contentEquals("completed"))
			return ideaRepo.findAllCompletedIdeasByUser(user.getId());
		else if (filter.contentEquals("liked")) {
			Set<Idea> likedIdeas = ideaRepo.findAllIdeasByUser(user.getId()).stream()
					.filter(idea -> idea.getLikes().stream().filter(like -> like.getLiked() == true) != null
							&& !idea.getLikes().isEmpty())
					.collect(Collectors.toSet());
			return likedIdeas;
		} else if (filter.contentEquals("myLiked")) {
			return ideaRepo.findAllLikedIdeasByUserId(user.getId());
		} else
			return ideaRepo.findAllIdeasByUserAndCategory(user.getId(), Long.parseLong(filter));
	}

	public Idea getIdeaById(Long ideaId) {
		return ideaRepo.findIdeaById(ideaId);
	}

	public Idea updateIdea(Idea inputIdea, Long ideaId) {
		Idea idea = ideaRepo.findIdeaById(ideaId);
		idea.setTitle(inputIdea.getTitle());
		idea.setDescription(inputIdea.getDescription());
		idea.setCompleted(inputIdea.getCompleted());
		idea.setCategories(inputIdea.getCategories());
		return ideaRepo.save(idea);

	}

	public Set<Idea> getIdeasByCategory(Long catId) {
		return ideaRepo.findAllIdeasByCategory(catId);
	}

	public Set<Idea> getAllIdeas(User user, String filter) {
		if (filter == null || filter.isEmpty() || filter.contentEquals("all"))
			return ideaRepo.findAllIdeas();
		else if (filter.contentEquals("completed"))
			return ideaRepo.findAllCompletedIdeas();
		else if (filter.contentEquals("liked")) {
			Set<Idea> likedIdeas = ideaRepo.findAllIdeas().stream()
					.filter(idea -> idea.getLikes().stream().filter(like -> like.getLiked() == true) != null
							&& !idea.getLikes().isEmpty())
					.collect(Collectors.toSet());
			return likedIdeas;
		} else if (filter.contentEquals("myLiked")) {
			return ideaRepo.findAllLikedIdeasByUserId(user.getId());
		} else
			return ideaRepo.findAllIdeasByCategory(Long.parseLong(filter));
	}

	public boolean isIdeaOwner(HttpServletRequest request, User user, Long ideaId) {
		if (request.isUserInRole("ROLE_ADMIN"))
			return true;
		else
			return getUserIdeas(user, "").stream().anyMatch(idea -> idea.getId() == ideaId);
	}

	public void deleteIdea(Idea idea, Long ideaId) {
		ideaRepo.deleteById(ideaId);		
	}

}
