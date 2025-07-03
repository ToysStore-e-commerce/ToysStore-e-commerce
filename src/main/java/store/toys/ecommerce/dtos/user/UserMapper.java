package store.toys.ecommerce.dtos.user;
import org.springframework.stereotype.Component;
import store.toys.ecommerce.dtos.review.ReviewInUserDTO;
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

        List<ReviewInUserDTO> reviewSummaries = null;
        if (user.getReviews() != null) {
            reviewSummaries = user.getReviews().stream()
                    .map(ReviewMapper::toSummaryDTO)
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
