package store.toys.ecommerce.exceptions;

public class CartForUserNotFoundException extends RuntimeException {
    public CartForUserNotFoundException(Long userId) {
        super("Cart for user with id " + userId + " not found");
    }
}
