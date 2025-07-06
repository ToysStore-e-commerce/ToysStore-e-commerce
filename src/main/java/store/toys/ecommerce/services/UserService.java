package store.toys.ecommerce.services;

import store.toys.ecommerce.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.toys.ecommerce.dtos.user.UserMapper;
import store.toys.ecommerce.dtos.user.UserRequestDTO;
import store.toys.ecommerce.dtos.user.UserResponseDTO;
//import store.toys.ecommerce.exceptions.EntityNotFoundException;
import store.toys.ecommerce.models.Category;
import store.toys.ecommerce.models.User;
import store.toys.ecommerce.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), id));
        user.getReviews().isEmpty();
        return userMapper.toResponse(user);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User newUser = userMapper.toEntity(userRequestDTO);
        User savedUser = userRepository.save(newUser);
        return userMapper.toResponse(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), id));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(User.class.getSimpleName(), id);
        }
        userRepository.deleteById(id);
    }
}
