package store.toys.ecommerce.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.toys.ecommerce.dtos.product.ProductResponseDTO;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.repositories.CategoryRepository;
import store.toys.ecommerce.repositories.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CloudinaryService cloudinaryService;

    @InjectMocks
    ProductService productService;

    Category category;
    Product product;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Plusies");

        product = new Product();
        product.setId(1L);
        product.setName("Pikachu Plush");
        product.setPrice(BigDecimal.valueOf(19.95));
        product.setFeatured(true);
        product.setCategory(category);
    }

    @Test
    void ShouldReturnAProductById() {
        // Given
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        // When
        ProductResponseDTO result = productService.getProductById(1L);
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Pikachu Plush");
        assertThat(result.getCategoryName()).isEqualTo("Plusies");

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void ShouldAddProductWithExistingCategorySuccessfullu() {
        Product newProduct = new Product();

    }

}
