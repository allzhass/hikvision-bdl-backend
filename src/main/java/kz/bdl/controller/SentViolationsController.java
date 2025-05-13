package kz.bdl.controller;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.service.SentViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sent-violations")
public class SentViolationsController {
    @Autowired
    private SentViolationsService sentViolationsService;

    @GetMapping
    public List<SentViolationsDTO> getAllSentViolations(Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getAllSentViolations();
        model.addAttribute("violations", violations);
        model.addAttribute("title", "All Sent Violations");
        return violations;
    }

    @GetMapping("/camera/{cameraId}")
    public List<SentViolationsDTO> getSentViolationsByCameraId(@PathVariable Long cameraId, Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByCameraId(cameraId);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Camera");
        return violations;
    }

    @GetMapping("/camera/name/{cameraName}")
    public List<SentViolationsDTO> getSentViolationsByCameraName(@PathVariable String cameraName, Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByCameraName(cameraName);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Camera Name");
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