package kz.bdl.controller;

import kz.bdl.dto.ViolationDTO;
import kz.bdl.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/violation")
public class ViolationController {
    @Autowired
    private ViolationService violationService;

    @GetMapping
    public List<ViolationDTO> getAllViolations() {
        return violationService.getAllViolations();
    }

    @PostMapping
    public ResponseEntity<String> addViolation(@RequestBody ViolationDTO violationDTO) {
        return violationService.addViolation(violationDTO);
    }

    @PutMapping
    public ResponseEntity<String> changeViolation(@RequestBody ViolationDTO violationDTO) {
        return violationService.changeViolation(violationDTO);
    }
}