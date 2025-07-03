package store.toys.ecommerce.dtos.cart;

import store.toys.ecommerce.dtos.cartItem.CartItemDTO;
import store.toys.ecommerce.dtos.cartItem.CartItemMapper;
import store.toys.ecommerce.models.Cart;
import store.toys.ecommerce.models.User;

import java.util.List;

public class CartMapper {

    public static Cart toEntity(CartDTO cartDTO, User user) {
        if (cartDTO == null || user == null) {
            return null;
        }
        return Cart.builder()
                .user(user)
                .totalPrice(cartDTO.getTotalPrice())
                .build();
    }

    public static CartDTO toDTO(Cart cart){
        if (cart == null) {
            return null;
        }
        List<CartItemDTO> itemsDTO = cart.getItems().stream()
                .map(CartItemMapper::toDTO)
                .toList();
        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .totalPrice(cart.getTotalPrice())
                .items(itemsDTO)
                .build();
    }
}
