package store.toys.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.models.Category;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}