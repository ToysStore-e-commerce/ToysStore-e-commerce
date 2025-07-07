package store.toys.ecommerce.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.toys.ecommerce.dtos.user.UserRequestDTO;
import store.toys.ecommerce.dtos.user.UserResponseDTO;
import store.toys.ecommerce.dtos.user.UserMapper;
import store.toys.ecommerce.exceptions.EntityNotFoundException;
import store.toys.ecommerce.models.User;
import store.toys.ecommerce.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock  private UserRepository userRepo;
    @Mock  private UserMapper userMapper;  

    @InjectMocks
    private UserService userService;

    private User ash;     
    private UserResponseDTO ashDto; 

    @BeforeEach
    void setUp() {
        ash = new User();
        ash.setId(1L);
        ash.setUsername("ashketchum");
        ash.setEmail("ash@pokemon.com");
        ash.setPassword("pikachu123");

        ashDto = new UserResponseDTO(1L, "ashketchum", "ash@pokemon.com", List.of());
    }



    @Test
    void getUserById_existing_returnsDto() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(ash));
        when(userMapper.toResponse(ash)).thenReturn(ashDto);

        UserResponseDTO dto = userService.getUserById(1L);

        assertThat(dto.getEmail()).isEqualTo("ash@pokemon.com");
        verify(userRepo).findById(1L);

    }

    @Test
    void getUserById_missing_throws404() {
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }
    
    @Test
    void createUser_savesAndReturnsDto() {
        UserRequestDTO req = new UserRequestDTO("misty", "misty@pokemon.com", "togepi456");
        User misty = new User(null, "misty", "misty@pokemon.com", "togepi456", List.of(), null);
        User saved = new User(2L, "misty", "misty@pokemon.com", "togepi456", List.of(), null);
        UserResponseDTO savedDto = new UserResponseDTO(2L, "misty", "misty@pokemon.com", List.of());

        when(userMapper.toEntity(req)).thenReturn(misty);
        when(userRepo.save(misty)).thenReturn(saved);
        when(userMapper.toResponse(saved)).thenReturn(savedDto);

        UserResponseDTO result = userService.createUser(req);

        assertThat(result.getId()).isEqualTo(2L);
        verify(userRepo).save(misty);
    }

    @Test
    void updateUser_changesFields() {
        UserRequestDTO patch = new UserRequestDTO("ash_new", "ash@poke.com", "newpass");
        when(userRepo.findById(1L)).thenReturn(Optional.of(ash));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0, User.class));
        when(userMapper.toResponse(any(User.class))).thenCallRealMethod();

        UserResponseDTO updated = userService.updateUser(1L, patch);

        assertThat(updated.getUsername()).isEqualTo("ash_new");
        verify(userRepo).save(any(User.class));
    }

    @Test
    void deleteUser_existing_invokesRepoDelete() {
        when(userRepo.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepo).deleteById(1L);
    }

    @Test
    void deleteUser_missing_throws404() {
        when(userRepo.existsById(42L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(42L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
