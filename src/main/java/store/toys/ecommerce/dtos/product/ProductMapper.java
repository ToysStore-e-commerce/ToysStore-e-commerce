package store.toys.ecommerce.dtos.product;

import org.springframework.stereotype.Component;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto, Category category) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .featured(dto.isFeatured())
                .category(category)
                .rating(0.0)
                .reviewCount(0)
                .build();
    }

    public ProductResponseDTO toDTO(Product product) {
        if (product == null) return null;

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .cloudinaryImageId(product.getCloudinaryImageId())
                .featured(product.isFeatured())
                .rating(product.getRating())
                .reviewCount(product.getReviewCount())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}

