package store.toys.ecommerce.dtos.user;
import org.springframework.stereotype.Component;
import store.toys.ecommerce.dtos.review.ReviewResponseDTO;
import store.toys.ecommerce.dtos.review.ReviewMapper;
import store.toys.ecommerce.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO dto){
        if (dto == null) {
            return null;
        }

        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

    }
    public UserResponseDTO toResponse(User user){
        if (user == null) {
            return null;
        }

        // Map the list of Review entities to a list of ReviewSummaryDTOs
        List<ReviewResponseDTO> reviewSummaries = null;
        if (user.getReviews() != null) {
            reviewSummaries = user.getReviews().stream()
                    .map(ReviewMapper::toSummaryDTO)  // Use the static method from ReviewMapper
                    .collect(Collectors.toList());
        }
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .reviews(reviewSummaries)
                .build();
    }
}
