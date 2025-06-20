package kz.bdl.service.impl;

import kz.bdl.converter.UserConverter;
import kz.bdl.dto.UserDTO;
import kz.bdl.entity.User;
import kz.bdl.exception.ResourceNotFoundException;
import kz.bdl.repository.UserRepository;
import kz.bdl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userConverter.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userConverter.toDTO(user);
    }

    @Override
    public List<UserDTO> getUsersByRoleId(Long roleId) {
        List<User> users = userRepository.findByRoleId(roleId);
        return users.stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getActiveUsers() {
        List<User> users = userRepository.findByIsActive(true);
        return users.stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addUser(UserDTO userDTO) {
        try {
            User user = userConverter.toEntity(userDTO);
            userRepository.save(user);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updateUser(UserDTO userDTO) {
        try {
            if (!userRepository.existsById(userDTO.getId())) {
                return ResponseEntity.badRequest().body("User not found with id: " + userDTO.getId());
            }
            
            // Get existing user to preserve password if not changed
            User existingUser = userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userDTO.getId()));
            
            User user = userConverter.toEntity(userDTO);
            
            // If password is empty or null, keep the existing password
            if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            }
            
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.badRequest().body("User not found with id: " + id);
            }
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deactivateUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            user.setIsActive(false);
            userRepository.save(user);
            return ResponseEntity.ok("User deactivated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deactivating user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> activateUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            user.setIsActive(true);
            userRepository.save(user);
            return ResponseEntity.ok("User activated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error activating user: " + e.getMessage());
        }
    }
} 