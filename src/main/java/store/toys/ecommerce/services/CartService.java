package store.toys.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.toys.ecommerce.models.Cart;
import store.toys.ecommerce.models.CartItem;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.models.User;
import store.toys.ecommerce.repositories.CartItemRepository;
import store.toys.ecommerce.repositories.CartRepository;
import store.toys.ecommerce.repositories.ProductRepository;
import store.toys.ecommerce.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Cart addProductToCart(Long userId, Long productId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User with id: " + userId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product with id: " + productId + " not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(()->{
                   Cart newCart = Cart.builder()
                           .user(user)
                           .totalPrice(BigDecimal.ZERO)
                           .build();
                   return cartRepository.save(newCart);
                });
        // we search if the product is in the cart already
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(1)
                    .cart(cart)
                    .build();
            cart.getItems().add(newItem);
        }
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item->item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Long userId, Long productId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User with id: " + userId + " not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->new RuntimeException("Cart for user with id: " + userId + " not found"));
        CartItem cartItemToRemove = cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Product is not in the cart"));
        cart.getItems().remove(cartItemToRemove);

        BigDecimal totalPrice = cart.getItems().stream()
                .map(item->item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }
}
