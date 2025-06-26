package store.toys.ecommerce.dtos.cart;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import store.toys.ecommerce.dtos.cartItem.CartItemDTO;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CartDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    private List<CartItemDTO> items;

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal totalPrice;
}

