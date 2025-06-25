package store.toys.ecommerce.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String imageUrl;

    private boolean featured;

    private double rating;
    private int reviewCount;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}

