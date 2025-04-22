package kz.bdl.converter;

import kz.bdl.dto.ViolationDTO;
import kz.bdl.entity.Violation;
import org.springframework.stereotype.Component;

@Component
public class ViolationConverter {
    public ViolationDTO toDTO(Violation violation) {
        return new ViolationDTO(violation.getId(), violation.getCode(), violation.getOperCode(), violation.getHikCode());
    }
    public Violation toEntity(ViolationDTO dto) {
        return new Violation(dto.getId(), dto.getCode(), dto.getOperCode(), dto.getHikCode());
    }
}