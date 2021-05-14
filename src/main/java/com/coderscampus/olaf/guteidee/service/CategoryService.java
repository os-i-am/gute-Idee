package com.coderscampus.olaf.guteidee.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.olaf.guteidee.domain.Category;
import com.coderscampus.olaf.guteidee.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;

	public Set<Category> getAllCategories() {
		return categoryRepo.findAllCategories();
	}

	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}

	public Category updateCategory(Category category) {
		if (checkIfCategoryExists(category) == true)
			return null;
		else
			return categoryRepo.save(category);
	}

	public void deleteCategory(Category category) {
		categoryRepo.delete(category);
	}

	public Set<Category> findByIdIn(List<Long> categoryIds) {
		return categoryRepo.findByIdIn(categoryIds);
	}

	public Boolean checkIfCategoryExists(Category category) {
		return (categoryRepo.findByTitle(category.getTitle()) != null);
	}

}
