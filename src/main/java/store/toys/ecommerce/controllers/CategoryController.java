package store.toys.ecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.category.CategoryDTO;
import store.toys.ecommerce.dtos.category.CategoryMapper;
import store.toys.ecommerce.dtos.product.ProductDTO;
import store.toys.ecommerce.dtos.product.ProductMapper;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.services.CategoryService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Get all categories",
            description = "Returns a list of all product categories."
    )
    @ApiResponse(responseCode = "200", description = "List of categories",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories().stream()
                .map(CategoryMapper::toDTO)
                // .map(category -> CategoryMapper.toDTO(category))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
        //return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Fetches a single category based on its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryMapper.toDTO(category));
    }

    @Operation(
            summary = "Create new category",
            description = "Creates a new category using the provided name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(CategoryMapper.toDTO(category), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update existing category",
            description = "Updates the name of an existing category by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(CategoryMapper.toDTO(updatedCategory));
    }

    @Operation(
            summary = "Delete category by ID",
            description = "Deletes a category from the system by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
        // return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}