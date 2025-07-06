package store.toys.ecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.toys.ecommerce.dtos.user.UserMapper;
import store.toys.ecommerce.dtos.user.UserRequestDTO;
import store.toys.ecommerce.dtos.user.UserResponseDTO;
import store.toys.ecommerce.models.User;
import store.toys.ecommerce.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Get all users",
            description = "Returns all users without exposing passwords"
    )
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns a user without exposing password"
    )
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user account with username, email, and password. Password must meet validation rules."
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update user by ID",
            description = "Updates an existing user’s details like username, email, and password. All fields are required."
    )
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        return userService.updateUser(id, dto);
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Deletes the specified user from the system. Returns 204 if successful or 404 if not found."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

