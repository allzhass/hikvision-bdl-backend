package kz.bdl.converter;

import kz.bdl.dto.RoleDTO;
import kz.bdl.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {
    
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }
    
    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return role;
    }
} 