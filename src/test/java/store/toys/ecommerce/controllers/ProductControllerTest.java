package store.toys.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import store.toys.ecommerce.dtos.product.ProductResponseDTO;
import store.toys.ecommerce.services.ProductService;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnProductById() throws Exception {
        // Given
        ProductResponseDTO response = ProductResponseDTO.builder()
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

        given(productService.getProductById(1L)).willReturn(response);

        // When + Then
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pikachu Plush"))
                .andExpect(jsonPath("$.price").value(19.99))
                .andExpect(jsonPath("$.categoryName").value("Plusies"));
    }
}
