package store.toys.ecommerce.dtos.product;

import org.springframework.stereotype.Component;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;

@Component
public class ProductMapper {
    public static Product toEntity(ProductDTO dto, Category category){
        if (dto == null) {
            return null;
        }
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .featured(dto.isFeatured())
                .category(category)
                // review/rating default values?
                .build();
    }
    public static ProductDTO toDTO(Product product){
        if (product == null) {
            return null;
        }
        return ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .cloudinaryImageId(product.getCloudinaryImageId())
                .featured(product.isFeatured())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
