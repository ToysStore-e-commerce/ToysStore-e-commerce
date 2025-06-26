package store.toys.ecommerce.dtos.review;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReviewDTO {

    @Min(value = 0)
    @Max(value = 5)
    private double rating;

    @NotBlank(message = "Review body is required")
    @Size(max = 500)
    private String body;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "User ID is required")
    private Long userId;
}
