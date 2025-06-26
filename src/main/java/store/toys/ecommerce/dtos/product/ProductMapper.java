package store.toys.ecommerce.dtos.product;

import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;

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
                .featured(product.isFeatured())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
