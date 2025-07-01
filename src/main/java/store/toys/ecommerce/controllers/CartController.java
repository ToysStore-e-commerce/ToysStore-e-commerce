package store.toys.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.cart.CartDTO;
import store.toys.ecommerce.dtos.cart.CartMapper;
import store.toys.ecommerce.models.Cart;
import store.toys.ecommerce.services.CartService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long userId, @PathVariable Long productId){
        Cart updatedCart = cartService.addProductToCart(userId, productId);
        return ResponseEntity.ok(CartMapper.toDTO(updatedCart));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable Long userId, @PathVariable Long productId){
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
