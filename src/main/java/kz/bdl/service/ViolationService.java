package kz.bdl.service;

import kz.bdl.dto.CameraDTO;
import kz.bdl.dto.ViolationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ViolationService {
    ViolationDTO getViolationById(Long id);
    List<ViolationDTO> getAllViolations();
    ResponseEntity<String> addViolation(ViolationDTO violationDTO);
    ResponseEntity<String> changeViolation(ViolationDTO violationDTO);
}