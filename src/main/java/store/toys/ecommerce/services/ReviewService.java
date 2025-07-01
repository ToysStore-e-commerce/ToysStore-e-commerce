package store.toys.ecommerce.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.toys.ecommerce.dtos.review.ReviewDTO;
import store.toys.ecommerce.dtos.review.ReviewMapper;
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
    private final UserRepository userRepository; // ✅ Add this safely

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Transactional
    public Review createReview(ReviewDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = ReviewMapper.toEntity(dto, user, product);
        Review savedReview = reviewRepository.save(review);

        // Update product rating and review count
        List<Review> productReviews = reviewRepository.findByProductId(product.getId());
        double totalRating = productReviews.stream().mapToDouble(Review::getRating).sum();
        product.setReviewCount(productReviews.size());
        product.setRating(totalRating / product.getReviewCount());

        productRepository.save(product);
        return savedReview;
    }
}