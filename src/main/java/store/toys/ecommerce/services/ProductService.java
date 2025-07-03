package store.toys.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.toys.ecommerce.dtos.cloudinary.CloudinaryResponseDTO;
import store.toys.ecommerce.dtos.product.ProductDTO;
import store.toys.ecommerce.dtos.product.ProductMapper;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.repositories.CategoryRepository;
import store.toys.ecommerce.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import store.toys.ecommerce.util.FileUploadUtil;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product " + id + " not found")); }

    public List<Product> getFilteredProducts(String name, Long categoryId, Boolean featured, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findAll((productRoot, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(productRoot.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(productRoot.get("category").get("id"), categoryId));
            }
            if (featured != null) {
                predicates.add(cb.equal(productRoot.get("featured"), featured));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(productRoot.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(productRoot.get("price"), maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Category productsCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category " + productDTO.getCategoryId() + " not found"));
        Product newProduct = ProductMapper.toEntity(productDTO, productsCategory);
        return productRepository.save(newProduct);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = getProductById(id);
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category " + productDTO.getCategoryId() + " not found"));
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setFeatured(productDTO.isFeatured());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Transactional
    public void uploadImage(final Long id, final MultipartFile file) {
        final Product product = this.productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryResponseDTO responseDTO = this.cloudinaryService.uploadFile(file, fileName);
        product.setImageUrl(responseDTO.getUrl());
        product.setCloudinaryImageId(responseDTO.getPublicId());
        this.productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new RuntimeException("Product with " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}