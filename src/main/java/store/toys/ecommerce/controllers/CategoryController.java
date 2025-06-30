package store.toys.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.category.CategoryDTO;
import store.toys.ecommerce.dtos.category.CategoryMapper;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.services.CategoryService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories().stream()
                .map(CategoryMapper::toDTO)
                // .map(category -> CategoryMapper.toDTO(category))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
        //return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto) {
        Category category = categoryService.createCategory(dto);
        return new ResponseEntity<>(CategoryMapper.toDTO(category), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        Category updatedCategory = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(CategoryMapper.toDTO(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
        // return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}