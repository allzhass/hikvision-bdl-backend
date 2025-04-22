package kz.bdl.service.impl;

import kz.bdl.converter.ViolationConverter;
import kz.bdl.dto.ViolationDTO;
import kz.bdl.repository.ViolationRepository;
import kz.bdl.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViolationServiceImpl implements ViolationService {
    @Autowired
    private ViolationRepository violationRepository;
    @Autowired private ViolationConverter violationConverter;

    @Override
    public List<ViolationDTO> getAllViolations() {
        return violationRepository.findAll().stream().map(violationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addViolation(ViolationDTO violationDTO) {
        violationRepository.save(violationConverter.toEntity(violationDTO));
        return ResponseEntity.ok("Violation added successfully");
    }

    @Override
    public ResponseEntity<String> changeViolation(ViolationDTO violationDTO) {
        if (!violationRepository.existsById(violationDTO.getId())) return ResponseEntity.badRequest().body("Violation not found");
        return addViolation(violationDTO);
    }
}
