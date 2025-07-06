package store.toys.ecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.toys.ecommerce.dtos.product.ProductRequestDTO;
import store.toys.ecommerce.dtos.product.ProductMapper;
import store.toys.ecommerce.dtos.product.ProductResponseDTO;
import store.toys.ecommerce.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(
            summary = "Get all products",
            description = "Returns a list of all available products with basic details including category, price, and rating."
    )
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Filter products by name, category, featured flag and price range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered product list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))))
    })
    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return ResponseEntity.ok(
                productService.getFilteredProducts(name, categoryId, featured, minPrice, maxPrice)
        );
    }

    @Operation(
            summary = "Get product by ID",
            description = "Returns a single product’s details by its ID. Throws 404 if not found."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with name, price, image URL, category, and featured flag. Automatically initializes rating and review count."
    )
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody @Valid ProductRequestDTO dto) {
        return new ResponseEntity<>(productService.createProduct(dto), HttpStatus.CREATED);
    }
    @PostMapping("/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable final Long id, @RequestParam final MultipartFile file) {
        this.productService.uploadImage(id, file);
        return ResponseEntity.ok("File upload successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }
    @Operation(
            summary = "Delete product by ID",
            description = "Deletes the specified product from the system. Returns 204 if successful or 404 if not found."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}



