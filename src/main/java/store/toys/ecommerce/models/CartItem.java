package store.toys.ecommerce.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.*;


@Entity
@Table(name = "cart_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String name;
    private BigDecimal price;
    private int quantity;

    // Optional relationship (to access product details if needed)
    @ManyToOne
    @JoinColumn(name = "product_ref_id")
    private Product product;
}
