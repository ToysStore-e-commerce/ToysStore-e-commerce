package store.toys.ecommerce.dtos.user;

import store.toys.ecommerce.models.User;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
