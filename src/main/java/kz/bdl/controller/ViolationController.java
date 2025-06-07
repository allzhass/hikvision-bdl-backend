package kz.bdl.controller;

import kz.bdl.dto.ViolationDTO;
import kz.bdl.service.ViolationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/violation")
@Slf4j
public class ViolationController {
    @Autowired
    private ViolationService violationService;

    @GetMapping
    public List<ViolationDTO> getAllViolations() {
        log.info("getAllViolations start");
        List<ViolationDTO> result = violationService.getAllViolations();
        log.info("getAllViolations end");
        return result;
    }

    @PostMapping
    public ResponseEntity<String> addViolation(@RequestBody ViolationDTO violationDTO) {
        log.info("addViolation start: {}", violationDTO);
        ResponseEntity<String> result = violationService.addViolation(violationDTO);
        log.info("addViolation end");
        return result;
    }

    @PutMapping
    public ResponseEntity<String> changeViolation(@RequestBody ViolationDTO violationDTO) {
        log.info("changeViolation start: {}", violationDTO);
        ResponseEntity<String> result = violationService.changeViolation(violationDTO);
        log.info("changeViolation end");
        return result;
    }
}