
package store.toys.ecommerce.dtos.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInUserDTO {
    private Long id;
    private double rating;
    private String body;
    private Long productId;
}