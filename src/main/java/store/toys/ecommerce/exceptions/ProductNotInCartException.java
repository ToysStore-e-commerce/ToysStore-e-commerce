package store.toys.ecommerce.exceptions;

public class ProductNotInCartException extends RuntimeException {
    public ProductNotInCartException(Long productId) {
        super("Product with id " + productId + " is not in the cart");
    }
}
