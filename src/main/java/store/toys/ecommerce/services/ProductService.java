package store.toys.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.toys.ecommerce.dtos.product.ProductDTO;
import store.toys.ecommerce.exceptions.ResourceNotFoundException;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.repositories.CategoryRepository;
import store.toys.ecommerce.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository  productRepo;
    private final CategoryRepository categoryRepo;

    public List<Product> findAll(){
        return productRepo.findAll();
    }
    public Product findById(Long id){
        return productRepo.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Product " + id + " not found")); }

    @Transactional
public Product create(ProductDTO dto) {
    Category category = categoryRepo.findById(dto.getCategoryId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Category " + dto.getCategoryId() + " not found"));

    Product product = Product.builder()
            .name(dto.getName())
            .price(dto.getPrice())
            .imageUrl(dto.getImageUrl())
            .featured(dto.isFeatured())
            .category(category)
            .rating(0.0)
            .reviewCount(0)
            .build();

    return productRepo.save(product);
}

@Transactional
public Product update(Long id, ProductDTO dto) {
    Product product = findById(id);
    Category category = categoryRepo.findById(dto.getCategoryId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Category " + dto.getCategoryId() + " not found"));

    product.setName(dto.getName());
    product.setPrice(dto.getPrice());
    product.setImageUrl(dto.getImageUrl());
    product.setFeatured(dto.isFeatured());
    product.setCategory(category);

    return productRepo.save(product);
}

    public void delete(Long id) {
        productRepo.deleteById(id);
    }
    }