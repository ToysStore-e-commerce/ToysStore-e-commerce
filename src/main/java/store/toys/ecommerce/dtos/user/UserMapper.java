package store.toys.ecommerce.dtos.user;

import store.toys.ecommerce.models.User;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto){
        if (dto == null) {
            return null;
        }

        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

    }
    public static UserResponseDTO toResponse(User user){
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
