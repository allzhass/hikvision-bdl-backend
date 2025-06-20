package kz.bdl.service;

import kz.bdl.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getUsersByRoleId(Long roleId);
    List<UserDTO> getActiveUsers();
    ResponseEntity<String> addUser(UserDTO userDTO);
    ResponseEntity<String> updateUser(UserDTO userDTO);
    ResponseEntity<String> deleteUser(Long id);
    ResponseEntity<String> deactivateUser(Long id);
    ResponseEntity<String> activateUser(Long id);
} 