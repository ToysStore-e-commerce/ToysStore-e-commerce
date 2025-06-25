package store.toys.ecommerce.dtos.cart;
import lombok.*;
import store.toys.ecommerce.dtos.cartItem.CartItemDTO;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CartDTO {

    private Long userId;

    private List<CartItemDTO> items;

    private BigDecimal totalPrice;
}

