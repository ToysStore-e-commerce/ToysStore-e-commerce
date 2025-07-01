package store.toys.ecommerce.dtos.cartItem;

import store.toys.ecommerce.models.Cart;
import store.toys.ecommerce.models.CartItem;
import store.toys.ecommerce.models.Product;

public class CartItemMapper {
    public static CartItem toEntity(CartItemDTO cartItemDTO, Product product, Cart cart){
        if (cartItemDTO == null) {
            return null;
        }
        return CartItem.builder()
                .quantity(cartItemDTO.getQuantity())
                .product(product)
                .cart(cart)
                .build();
    }

    public static CartItemDTO toDTO(CartItem cartItem){
        return CartItemDTO.builder()
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
