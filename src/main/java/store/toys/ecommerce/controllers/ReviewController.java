package store.toys.ecommerce.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.review.ReviewRequestDTO;
import store.toys.ecommerce.dtos.review.ReviewMapper;
import store.toys.ecommerce.models.Review;
import store.toys.ecommerce.services.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewRequestDTO>> getReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long userId
    ) {
        List<Review> reviews;

        if (productId != null) {
            reviews = reviewService.getReviewsByProductId(productId);
        } else if (userId != null) {
            reviews = reviewService.getReviewsByUserId(userId);
        } else {
            return ResponseEntity.badRequest().build();
        }

        List<ReviewRequestDTO> result = reviews.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ReviewRequestDTO> createReview(@RequestBody @Valid ReviewRequestDTO dto) {
        Review saved = reviewService.createReview(dto);
        return ResponseEntity.ok(ReviewMapper.toDTO(saved));
    }
}