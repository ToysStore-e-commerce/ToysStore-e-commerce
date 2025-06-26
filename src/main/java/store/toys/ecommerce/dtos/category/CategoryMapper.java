package store.toys.ecommerce.dtos.category;

import store.toys.ecommerce.models.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public static CategoryResponseDTO toResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}