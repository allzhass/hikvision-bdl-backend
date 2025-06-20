package kz.bdl.service.impl;

import kz.bdl.converter.RoleConverter;
import kz.bdl.dto.RoleDTO;
import kz.bdl.entity.Role;
import kz.bdl.exception.ResourceNotFoundException;
import kz.bdl.repository.RoleRepository;
import kz.bdl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return roleConverter.toDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));
        return roleConverter.toDTO(role);
    }

    @Override
    public ResponseEntity<String> addRole(RoleDTO roleDTO) {
        try {
            Role role = roleConverter.toEntity(roleDTO);
            roleRepository.save(role);
            return ResponseEntity.ok("Role added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding role: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updateRole(RoleDTO roleDTO) {
        try {
            if (!roleRepository.existsById(roleDTO.getId())) {
                return ResponseEntity.badRequest().body("Role not found with id: " + roleDTO.getId());
            }
            Role role = roleConverter.toEntity(roleDTO);
            roleRepository.save(role);
            return ResponseEntity.ok("Role updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating role: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteRole(Long id) {
        try {
            if (!roleRepository.existsById(id)) {
                return ResponseEntity.badRequest().body("Role not found with id: " + id);
            }
            roleRepository.deleteById(id);
            return ResponseEntity.ok("Role deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting role: " + e.getMessage());
        }
    }
} 