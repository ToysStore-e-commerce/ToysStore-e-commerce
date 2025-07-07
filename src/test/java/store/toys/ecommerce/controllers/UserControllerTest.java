package store.toys.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import store.toys.ecommerce.dtos.user.UserRequestDTO;
import store.toys.ecommerce.dtos.user.UserResponseDTO;
import store.toys.ecommerce.models.User;
import store.toys.ecommerce.services.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getAllUsers_returnsList() throws Exception {
        UserResponseDTO ash   = UserResponseDTO.builder()
                .id(1L).username("ash")
                .email("ash@pokemon.com")
                .reviews(List.of())
                .build();
        UserResponseDTO misty = UserResponseDTO.builder()
                .id(2L).username("misty")
                .email("misty@pokemon.com")
                .reviews(List.of())
                .build();

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(ash, misty));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("ash"))
                .andExpect(jsonPath("$[1].email").value("misty@pokemon.com"));
    }

    @Test
    void getUserById_returnsUser() throws Exception {
        UserResponseDTO ash = UserResponseDTO.builder()
                .id(1L).username("ash")
                .email("ash@pokemon.com")
                .reviews(List.of()).build();

        Mockito.when(userService.getUserById(1L)).thenReturn(ash);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ash"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createUser_returnsCreated() throws Exception {
        UserRequestDTO req = new UserRequestDTO("misty", "misty@pokemon.com", "togepi456");
        UserResponseDTO saved = UserResponseDTO.builder()
                .id(2L)
                .username("misty")
                .email("misty@pokemon.com")
                .reviews(List.of()).build();

        Mockito.when(userService.createUser(any(UserRequestDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value("misty"));
    }

    @Test
    void updateUser_returnsUpdated() throws Exception {
        UserRequestDTO updateReq = new UserRequestDTO("ash_new", "ash_new@pokemon.com", "pikachu007");
        UserResponseDTO updated = UserResponseDTO.builder()
                .id(1L)
                .username("ash_new")
                .email("ash_new@pokemon.com")
                .reviews(List.of()).build();

        Mockito.when(userService.updateUser(Mockito.eq(1L), any(UserRequestDTO.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ash_new"))
                .andExpect(jsonPath("$.email").value("ash_new@pokemon.com"));
    }

    @Test
    void deleteUser_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).deleteUser(1L);
    }
}
