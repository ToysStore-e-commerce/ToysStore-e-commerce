package store.toys.ecommerce.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 70)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String imageUrl;
    private String cloudinaryImageId;

    private boolean featured;

    private double rating;
    private int reviewCount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    // to avoid infinite recursion
    // @ToString.Exclude: prevents the list of reviews from being printed automatically when calling System.out.println(product)
    // or using @ToString, which could cause an infinite recursion if Review also prints the product.
    @ToString.Exclude
    // @EqualsAndHashCode.Exclude: prevents the list of reviews from being included when comparing two Product objects,
    // which could cause problems if there are circular references or very large lists.
    @EqualsAndHashCode.Exclude
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CartItem> cartItems = new ArrayList<>();


}

