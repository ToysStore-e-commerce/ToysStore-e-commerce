package store.toys.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.toys.ecommerce.models.User;

@Repository
    public interface UserRepository extends JpaRepository<User, Long> {
}
