package store.toys.ecommerce.dtos.user;

import lombok.*;
import store.toys.ecommerce.dtos.review.ReviewInUserDTO;

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
    private List<ReviewInUserDTO> reviews;
}
