package store.toys.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.toys.ecommerce.dtos.product.ProductDTO;
import store.toys.ecommerce.dtos.product.ProductMapper;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.repositories.CategoryRepository;
import store.toys.ecommerce.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product " + id + " not found")); }

    @Transactional
    public Product createProduct(ProductDTO dto) {
        Category productsCategory = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category " + dto.getCategoryId() + " not found"));
        Product newProduct = ProductMapper.toEntity(dto, productsCategory);
        return productRepository.save(newProduct);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO dto) {
        Product product = getProductById(id);
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category " + dto.getCategoryId() + " not found"));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setFeatured(dto.isFeatured());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new RuntimeException("Product with " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}