package store.toys.ecommerce.dtos.cartItem;
import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CartItemDTO {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private int quantity;
}

