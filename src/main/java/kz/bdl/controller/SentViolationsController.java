package kz.bdl.controller;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.service.SentViolationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sent-violations")
@Slf4j
public class SentViolationsController {
    @Autowired
    private SentViolationsService sentViolationsService;

    @GetMapping
    public List<SentViolationsDTO> getAllSentViolations(Model model) {
        log.info("getAllSentViolations start");
        List<SentViolationsDTO> violations = sentViolationsService.getAllSentViolations();
        model.addAttribute("violations", violations);
        model.addAttribute("title", "All Sent Violations");
        log.info("getAllSentViolations end");
        return violations;
    }

    @GetMapping("/camera/{cameraId}")
    public List<SentViolationsDTO> getSentViolationsByCameraId(@PathVariable Long cameraId, Model model) {
        log.info("getSentViolationsByCameraId start: {}", cameraId);
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByCameraId(cameraId);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Camera");
        log.info("getSentViolationsByCameraId end");
        return violations;
    }

    @GetMapping("/camera/name/{cameraName}")
    public List<SentViolationsDTO> getSentViolationsByCameraName(@PathVariable String cameraName, Model model) {
        log.info("getSentViolationsByCameraName start: {}", cameraName);
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByCameraName(cameraName);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Camera Name");
        log.info("getSentViolationsByCameraName end");
        return violations;
    }

    @GetMapping("/camera/ip/{cameraIp}")
    public List<SentViolationsDTO> getSentViolationsByCameraIp(@PathVariable String cameraIp, Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByCameraIp(cameraIp);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Camera IP");
        return violations;
    }
} 