package store.toys.ecommerce.dtos.product;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private boolean featured;
    private double rating;
    private int reviewCount;
    private Long categoryId;
    private String categoryName;
}