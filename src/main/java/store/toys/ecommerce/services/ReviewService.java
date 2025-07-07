package store.toys.ecommerce.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.toys.ecommerce.dtos.review.ReviewRequestDTO;
import store.toys.ecommerce.dtos.review.ReviewMapper;
import store.toys.ecommerce.exceptions.EntityNotFoundException;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.models.Review;
import store.toys.ecommerce.models.User;
import store.toys.ecommerce.repositories.ProductRepository;
import store.toys.ecommerce.repositories.ReviewRepository;
import store.toys.ecommerce.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Transactional
    public Review createReview(ReviewRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(Product.class.getSimpleName(), dto.getProductId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), dto.getUserId()));

        Review review = ReviewMapper.toEntity(dto, user, product);
        Review savedReview = reviewRepository.save(review);
        
        List<Review> productReviews = reviewRepository.findByProductId(product.getId());
        double totalRating = productReviews.stream().mapToDouble(Review::getRating).sum();
        product.setReviewCount(productReviews.size());
        product.setRating(totalRating / product.getReviewCount());

        productRepository.save(product);
        return savedReview;
    }
}