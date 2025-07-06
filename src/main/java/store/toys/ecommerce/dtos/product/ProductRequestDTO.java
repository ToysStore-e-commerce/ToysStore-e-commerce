package store.toys.ecommerce.dtos.product;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private boolean featured;
    private Long categoryId;
}

