package store.toys.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import store.toys.ecommerce.dtos.product.ProductResponseDTO;
import store.toys.ecommerce.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnProductById() throws Exception {
        ProductResponseDTO pikachu = ProductResponseDTO.builder()
                .id(1L)
                .name("Pikachu Plush")
                .price(new BigDecimal("19.99"))
                .imageUrl("https://example.com/pikachu.png")
                .featured(true)
                .rating(4.5)
                .reviewCount(10)
                .categoryId(1L)
                .categoryName("Plusies")
                .build();

        when(productService.getProductById(1L)).thenReturn(pikachu);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pikachu Plush"))
                .andExpect(jsonPath("$.price").value(19.99))
                .andExpect(jsonPath("$.categoryName").value("Plusies"));
    }

    @Test
    void shouldReturnFilteredProducts() throws Exception {
        ProductResponseDTO product = ProductResponseDTO.builder()
                .id(2L)
                .name("Charizard Figure")
                .price(new BigDecimal("39.99"))
                .imageUrl("https://example.com/charizard.png")
                .featured(false)
                .rating(4.7)
                .reviewCount(12)
                .categoryId(2L)
                .categoryName("Figures")
                .build();

        when(productService.getFilteredProducts(eq("Charizard"), eq(2L), eq(false),
                eq(new BigDecimal("30.00")), eq(new BigDecimal("50.00"))))
                .thenReturn(List.of(product));

        mockMvc.perform(get("/api/products/filter")
                        .param("name", "Charizard")
                        .param("categoryId", "2")
                        .param("featured", "false")
                        .param("minPrice", "30.00")
                        .param("maxPrice", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Charizard Figure"))
                .andExpect(jsonPath("$[0].price").value(39.99));
    }
}
