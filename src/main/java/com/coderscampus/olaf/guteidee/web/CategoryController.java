package com.coderscampus.olaf.guteidee.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.olaf.guteidee.domain.Category;
import com.coderscampus.olaf.guteidee.domain.User;
import com.coderscampus.olaf.guteidee.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category/getAllCategories")
	@ResponseBody
	public Set<Category> getAllCategories() {
		return (categoryService.getAllCategories());
	}

	@GetMapping("/admin/editCategories")
	public String getAdminDashboard(@AuthenticationPrincipal User user, ModelMap model) {
		model.put("user", user);
		model.put("newCategory", new Category());
		model.put("categories", categoryService.getAllCategories());
		return "editCategories";
	}

	@PostMapping("/admin/editCategory")
	@ResponseBody
	public Category editCategory(@AuthenticationPrincipal User user, @RequestBody Category category) {
		return categoryService.updateCategory(category);
	}

	@PostMapping("/admin/deleteCategory")
	@ResponseBody
	public Boolean deleteCategory(@AuthenticationPrincipal User user, @RequestBody Category category) {
		categoryService.deleteCategory(category);
		return true;
	}

	@PostMapping("/admin/createCategory")
	public String createCategory(@AuthenticationPrincipal User user, ModelMap model, Category category) {
		categoryService.createCategory(category);
		return "redirect:/admin/editCategories";
	}

}
