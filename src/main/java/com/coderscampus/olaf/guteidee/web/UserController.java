package com.coderscampus.olaf.guteidee.web;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.olaf.guteidee.domain.User;
import com.coderscampus.olaf.guteidee.service.CategoryService;
import com.coderscampus.olaf.guteidee.service.IdeaService;
import com.coderscampus.olaf.guteidee.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private IdeaService ideaService;


	@GetMapping("/")
	public String getIndex(@AuthenticationPrincipal User user, ModelMap model, String filter) {
		model.put("user", user);
		model.put("categories", categoryService.getAllCategories());
		model.put("ideas", ideaService.getAllIdeas(user, filter));
		return "index";
	}

	@GetMapping("/register")
	public String getRegister(ModelMap model) {
		model.put("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		userService.registerNewUser(user);
		return "redirect:/user";
	}

	@PostMapping("/exists")
	@ResponseBody
	public Boolean checkIfUserExists(@RequestBody User user) {
		return userService.checkIfUserExists(user);
	}

	@GetMapping("/login")
	public String getLogin(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("user", user);
		return "login";
	}

	@GetMapping("/user")
	public String getUserDashboard(@AuthenticationPrincipal User user, ModelMap model, String filter) {
		model.put("user", user);
		model.put("categories", categoryService.getAllCategories());
		model.put("ideas", ideaService.getUserIdeas(user, filter));
		return "userDashboard";
	}

	@GetMapping("/user/{userId}")
	public String getUserIdeas(@AuthenticationPrincipal User user, @PathVariable Long userId, ModelMap model, String filter) {
		model.put("user", user);
		model.put("ideasOwner", userService.getNameById(userId));
		model.put("categories", categoryService.getAllCategories());
		model.put("ideas", ideaService.getUserIdeas(userService.findById(userId), filter));
		return "userIdeas";
	}

	@GetMapping("/admin")
	public String getAdminDashboard(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("user", user);
		return "adminDashboard";
	}

	@GetMapping("/admin/listAllUsers")
	public String listAllUsers(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("user", user);
		model.put("users", userService.getAllUsersWithRoles());
		return "listAllUsers";
	}

	@GetMapping("/user/editUser/{userId}")
	public String getEditUser(@AuthenticationPrincipal User user, HttpServletRequest request, @PathVariable Long userId, ModelMap model) {
		if (user.getId() == userId || request.isUserInRole("ROLE_ADMIN")) {
			model.put("user", user);
			User editUser = userService.getSingleUserWithRoles(userId);
			model.put("editUser", editUser);
			Boolean isAdmin = userService.checkForAdmin(editUser);
			model.put("isAdmin", isAdmin);
			return "editUser";
		} else
			return "accessDenied";
	}
	
	@PostMapping("/user/editUser/")
	public String postEditUser(User inputUser) {
		userService.editUserDetails(inputUser);
		return "redirect:/user";
	}
	
	@PostMapping("/user/deleteUser/")
	public String postDeleteUser(User inputUser) {
		userService.deleteUserDetails(inputUser);
		return "redirect:/admin";
	}

	@PostMapping("/user/likeIdea")
	public String postlikeIdea(@AuthenticationPrincipal User user, Long ideaId, Boolean liked) {
		userService.likeIdea(ideaId, user, liked);
		return "redirect:/";
	}

	@GetMapping("/accessDenied")
	public String getAccessDenied(ModelMap model) {
		return "accessDenied";
	}

}
