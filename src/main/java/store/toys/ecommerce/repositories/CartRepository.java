package store.toys.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import store.toys.ecommerce.models.Cart;
import store.toys.ecommerce.models.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
