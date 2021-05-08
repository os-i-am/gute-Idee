package com.coderscampus.olaf.guteidee.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.olaf.guteidee.domain.Idea;
import com.coderscampus.olaf.guteidee.domain.User;
import com.coderscampus.olaf.guteidee.service.CategoryService;
import com.coderscampus.olaf.guteidee.service.IdeaService;
import com.coderscampus.olaf.guteidee.service.UserService;

@Controller
public class IdeaController {

	@Autowired
	private IdeaService ideaService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;

	@GetMapping("/user/createIdea")
	public String getUserDashboard(@AuthenticationPrincipal User user, ModelMap model) {
		Idea idea = ideaService.CreateEmptyIdeaWithCategories();
		model.put("idea", idea);
		model.put("user", user);
		return "createIdea";
	}

	@PostMapping("/user/createIdea")
	public String postCreateIdea(@AuthenticationPrincipal User user, Idea idea) {
		ideaService.saveNewIdea(user, idea);
		return "redirect:/user";
	}

	@GetMapping("/idea/{ideaId}")
	public String getSingleIdea(@AuthenticationPrincipal User user, @PathVariable Long ideaId, ModelMap model) {
			Idea idea = ideaService.getIdeaById(ideaId);
			if (user != null) {
				Boolean liked = userService.checkForLike(idea, user);
				model.put("liked", liked);
			}
			model.put("idea", idea);
			model.put("user", user);
			return "idea";
	}

	@GetMapping("/user/editIdea/{ideaId}")
	public String editIdea(@AuthenticationPrincipal User user, HttpServletRequest request, @PathVariable Long ideaId, ModelMap model) {
		if (ideaService.isIdeaOwner(request, user, ideaId)) {
			model.put("categories", categoryService.getAllCategories());
			Idea idea = ideaService.getIdeaById(ideaId);
			model.put("idea", idea);
			model.put("user", user);
			return "editIdea";
		} else
			return "accessDenied";
	}

	@PostMapping("/user/editIdea/{ideaId}")
	public String updateIdea(@AuthenticationPrincipal User user, @PathVariable Long ideaId, Idea idea) {
		ideaService.updateIdea(idea, ideaId);
		return "redirect:/user";
	}
	
	@PostMapping("/user/deleteIdea/{ideaId}")
	public String deleteIdea(@AuthenticationPrincipal User user, @PathVariable Long ideaId) {
		ideaService.deleteIdea(ideaId);
		return "redirect:/user";
	}

}
