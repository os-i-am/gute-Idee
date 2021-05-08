package com.coderscampus.olaf.guteidee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.olaf.guteidee.domain.Category;
import com.coderscampus.olaf.guteidee.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;

	public List<Category> getAllCategories() {
		return categoryRepo.findAllOrdered();
	}

	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}

	public Category updateCategory(Category category) {
		return categoryRepo.save(category);
	}

	public void deleteCategory(Category category) {
		System.out.println(category);
		categoryRepo.delete(category);
	}

}
