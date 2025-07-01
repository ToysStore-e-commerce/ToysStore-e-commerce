package store.toys.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import store.toys.ecommerce.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
