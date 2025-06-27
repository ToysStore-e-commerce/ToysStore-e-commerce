package store.toys.ecommerce.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.product.ProductDTO;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService products;

    @GetMapping
    public List<Product> getAll() {
        return products.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id){
        return products.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid ProductDTO p){
        return products.create(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestBody @Valid ProductDTO p){
        return products.update(id, p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        products.delete(id);
    }
    @GetMapping("/filter")
    public List<Product> filter(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Long categoryId,
                                @RequestParam(required = false) BigDecimal minPrice,
                                @RequestParam(required = false) BigDecimal maxPrice) {

        if (name != null)
            return products.filterByName(name);
        if (categoryId != null)
            return products.filterByCategory(categoryId);
        if (minPrice != null && maxPrice != null)
            return products.filterByPrice(minPrice, maxPrice);

        return products.findAll();
    }
}

