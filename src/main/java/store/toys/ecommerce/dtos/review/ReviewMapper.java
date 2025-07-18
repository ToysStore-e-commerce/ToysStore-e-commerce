package store.toys.ecommerce.dtos.review;

import store.toys.ecommerce.models.Product;
import store.toys.ecommerce.models.Review;
import store.toys.ecommerce.models.User;

public class ReviewMapper {

    public static Review toEntity(ReviewRequestDTO dto, User user, Product product) {
        if (dto == null) {
            return null;
        }
        return Review.builder()
                .rating(dto.getRating())
                .body(dto.getBody())
                .product(product)
                .user(user)
                .build();
    }

    public static ReviewRequestDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }
        return ReviewRequestDTO.builder()
                .rating(review.getRating())
                .body(review.getBody())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .build();
    }

    public static ReviewResponseDTO toSummaryDTO(Review review) {
        if (review == null) {
            return null;
        }
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .body(review.getBody())
                .productId(review.getProduct() != null ? review.getProduct().getId() : null)
                .build();
    }
}