package store.toys.ecommerce.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import store.toys.ecommerce.dtos.product.ProductMapper;
import store.toys.ecommerce.dtos.product.ProductRequestDTO;
import store.toys.ecommerce.dtos.product.ProductResponseDTO;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.repositories.CategoryRepository;
import store.toys.ecommerce.repositories.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
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
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(1L, "Pikachu Plush", new BigDecimal("19.95"), "url", true, 0.0, 0, 1L, "Plusies");
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productMapper.toDTO(product)).willReturn(productResponseDTO);
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
    void shouldAddProductWithExistingCategorySuccessfully() {
        // Given
        ProductRequestDTO dto = ProductRequestDTO.builder()
                .name("Pikachu Plush")
                .price(new BigDecimal("19.95"))
                .imageUrl("https://example.com/image.png")
                .featured(true)
                .categoryId(1L)
                .build();

        Product productToSave = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .featured(dto.isFeatured())
                .category(category)
                .rating(0.0)
                .reviewCount(0)
                .build();

        Product savedProduct = Product.builder()
                .id(1L)
                .name(dto.getName())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .featured(dto.isFeatured())
                .category(category)
                .rating(0.0)
                .reviewCount(0)
                .build();

        ProductResponseDTO responseDTO = ProductResponseDTO.builder()
                .id(1L)
                .name("Pikachu Plush")
                .price(new BigDecimal("19.95"))
                .imageUrl("https://example.com/image.png")
                .featured(true)
                .rating(0.0)
                .reviewCount(0)
                .categoryId(1L)
                .categoryName("Plusies")
                .build();

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(productMapper.toEntity(dto, category)).willReturn(productToSave);
        given(productRepository.save(productToSave)).willReturn(savedProduct);
        given(productMapper.toDTO(savedProduct)).willReturn(responseDTO);

        // When
        ProductResponseDTO result = productService.createProduct(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Pikachu Plush");
        assertThat(result.getCategoryName()).isEqualTo("Plusies");

        verify(categoryRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toEntity(dto, category);
        verify(productRepository, times(1)).save(productToSave);
        verify(productMapper, times(1)).toDTO(savedProduct);
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Given
        ProductRequestDTO dto = ProductRequestDTO.builder()
                .name("Updated Pikachu Plush")
                .price(new BigDecimal("24.99"))
                .imageUrl("https://example.com/image.png")
                .featured(false)
                .categoryId(1L)
                .build();

        Product updated = Product.builder()
                .id(1L)
                .name(dto.getName())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .featured(dto.isFeatured())
                .category(category)
                .rating(0.0)
                .reviewCount(0)
                .build();

        ProductResponseDTO responseDTO = ProductResponseDTO.builder()
                .id(1L)
                .name("Updated Pikachu Plush")
                .price(new BigDecimal("24.99"))
                .imageUrl("https://example.com/image.png")
                .featured(false)
                .rating(0.0)
                .reviewCount(0)
                .categoryId(1L)
                .categoryName("Plusies")
                .build();

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(productRepository.save(product)).willReturn(updated);
        given(productMapper.toDTO(updated)).willReturn(responseDTO);

        // When
        ProductResponseDTO result = productService.updateProduct(1L, dto);

        // Then
        assertThat(result.getName()).isEqualTo("Updated Pikachu Plush");
        verify(productRepository).save(product);
    }
    @Test
    void shouldDeleteProductSuccessfully() {
        // Given
        given(productRepository.existsById(1L)).willReturn(true);

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentProduct() {
        given(productRepository.existsById(999L)).willReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product with 999 not found");

        verify(productRepository, never()).deleteById(999L);
    }

    @Test
    void shouldReturnFilteredProductsByNameAndCategory() {
        // Given
        String name = "pikachu";
        Long categoryId = 1L;
        Boolean featured = true;
        BigDecimal minPrice = new BigDecimal("10.00");
        BigDecimal maxPrice = new BigDecimal("30.00");

        List<Product> filteredProducts = List.of(product);

        ProductResponseDTO responseDTO = ProductResponseDTO.builder()
                .id(1L)
                .name("Pikachu Plush")
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .featured(product.isFeatured())
                .rating(product.getRating())
                .reviewCount(product.getReviewCount())
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();

        given(productRepository.findAll((Specification<Product>) any())).willReturn(filteredProducts);
        given(productMapper.toDTO(product)).willReturn(responseDTO);

        // When
        List<ProductResponseDTO> result = productService.getFilteredProducts(
                name, categoryId, featured, minPrice, maxPrice
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).containsIgnoringCase("pikachu");

        verify(productRepository).findAll((Specification<Product>) any());
        verify(productMapper).toDTO(product);
    }




}
