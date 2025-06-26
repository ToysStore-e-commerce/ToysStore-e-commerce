package store.toys.ecommerce.services;

import store.toys.ecommerce.dtos.category.CategoryDTO;
import store.toys.ecommerce.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO dto);
    List<Category> getAllCategories();
    Category updateCategory(Long id, CategoryDTO dto);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
}
