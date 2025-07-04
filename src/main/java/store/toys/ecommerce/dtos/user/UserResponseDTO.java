package store.toys.ecommerce.dtos.user;

import lombok.*;
import store.toys.ecommerce.dtos.review.ReviewResponseDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<ReviewResponseDTO> reviews;
}
