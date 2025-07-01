package store.toys.ecommerce.dtos.category;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import store.toys.ecommerce.models.Category;

@Component
public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public static CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}