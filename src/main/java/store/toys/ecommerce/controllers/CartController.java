package store.toys.ecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Add product to user's cart",
            description = "Adds one unit of the specified product to the user's cart."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added to cart"),
            @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long userId, @PathVariable Long productId){
        Cart updatedCart = cartService.addProductToCart(userId, productId);
        return ResponseEntity.ok(CartMapper.toDTO(updatedCart));
    }

    @Operation(
            summary = "Remove product from user's cart",
            description = "Removes one unit of the specified product from the user's cart."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed from cart"),
            @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable Long userId, @PathVariable Long productId){
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
