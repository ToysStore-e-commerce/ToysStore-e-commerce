package store.toys.ecommerce.dtos.product;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 70)
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(http|https)://.*$", message = "Must be a valid URL")
    private String imageUrl;

    private boolean featured;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private String categoryName;

    private double rating;
    private int reviewCount;
}

