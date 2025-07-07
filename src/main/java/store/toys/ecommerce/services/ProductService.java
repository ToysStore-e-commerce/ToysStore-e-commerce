package store.toys.ecommerce.services;

import store.toys.ecommerce.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.toys.ecommerce.dtos.cloudinary.CloudinaryDTO;
import store.toys.ecommerce.dtos.product.ProductRequestDTO;
import store.toys.ecommerce.dtos.product.ProductMapper;
import store.toys.ecommerce.exceptions.ProductNotFoundException;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.repositories.CategoryRepository;
import store.toys.ecommerce.repositories.ProductRepository;
import store.toys.ecommerce.dtos.product.ProductResponseDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import store.toys.ecommerce.util.FileUploadUtil;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class.getSimpleName(), id));
        return productMapper.toDTO(product);
    }

    public List<ProductResponseDTO> getFilteredProducts(String name, Long categoryId, Boolean featured,
                                                        BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (featured != null) {
                predicates.add(cb.equal(root.get("featured"), featured));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return products.stream().map(productMapper::toDTO).toList();
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()-> new EntityNotFoundException(Category.class.getSimpleName(), dto.getCategoryId()));
        Product newProduct = productMapper.toEntity(dto, category);
        return productMapper.toDTO(productRepository.save(newProduct));
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Product.class.getSimpleName(), id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()-> new EntityNotFoundException(Category.class.getSimpleName(), dto.getCategoryId()));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setFeatured(dto.isFeatured());
        product.setCategory(category);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    public void uploadImage(final Long id, final MultipartFile file) {
        final Product product = this.productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
        final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        final CloudinaryDTO responseDTO = this.cloudinaryService.uploadFile(file, fileName);
        product.setImageUrl(responseDTO.getUrl());
        product.setCloudinaryImageId(responseDTO.getPublicId());
        this.productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException(Product.class.getSimpleName(), id);
        }
        productRepository.deleteById(id);
    }
}
