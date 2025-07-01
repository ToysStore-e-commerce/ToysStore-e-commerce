package store.toys.ecommerce.dtos.review;

import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.models.Review;
import store.toys.ecommerce.models.User;

public class ReviewMapper {

    public static Review toEntity(ReviewDTO dto, User user, Product product) {
        return Review.builder()
                .rating(dto.getRating())
                .body(dto.getBody())
                .user(user)
                .product(product)
                .build();
    }

    public static ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
                .rating(review.getRating())
                .body(review.getBody())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .build();
    }
}