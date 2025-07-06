package store.toys.ecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.review.ReviewDTO;
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

    @Operation(
            summary = "Get reviews by product or user ID",
            description = "Returns all reviews filtered by productId or userId. One filter is required."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reviews returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Missing filter parameter", content = @Content)
    })

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getReviews(
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

        List<ReviewDTO> result = reviews.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Create a review",
            description = "Submits a new review for a product by a user. Requires valid rating, body, productId and userId."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully",
                    content = @Content(schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody @Valid ReviewDTO dto) {
        Review saved = reviewService.createReview(dto);
        return ResponseEntity.ok(ReviewMapper.toDTO(saved));
    }
}