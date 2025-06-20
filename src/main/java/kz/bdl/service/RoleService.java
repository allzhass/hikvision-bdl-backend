package kz.bdl.service;

import kz.bdl.dto.RoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    RoleDTO getRoleById(Long id);
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleByName(String name);
    ResponseEntity<String> addRole(RoleDTO roleDTO);
    ResponseEntity<String> updateRole(RoleDTO roleDTO);
    ResponseEntity<String> deleteRole(Long id);
} 