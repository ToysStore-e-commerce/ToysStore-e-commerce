package store.toys.ecommerce.dtos.user;
import org.springframework.stereotype.Component;
import store.toys.ecommerce.models.User;

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

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
