package kz.bdl.controller;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.service.SentViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sent-violations-view")
public class SentViolationsViewController {
    @Autowired
    private SentViolationsService sentViolationsService;

    @GetMapping
    public String getAllSentViolations(Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getAllSentViolations();
        model.addAttribute("violations", violations);
        model.addAttribute("title", "All Sent Violations");
        return "sent-violations/list";
    }

    @GetMapping("/camera/{cameraId}")
    public String getSentViolationsByCameraId(@PathVariable Long cameraId, Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByCameraId(cameraId);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Camera");
        return "sent-violations/list";
    }

    @GetMapping("/apk/{apkId}")
    public String getSentViolationsByAPKId(@PathVariable Long apkId, Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByAPKId(apkId);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by APK");
        return "sent-violations/list";
    }

    @GetMapping("/violation/{violationId}")
    public String getSentViolationsByViolationId(@PathVariable Long violationId, Model model) {
        List<SentViolationsDTO> violations = sentViolationsService.getSentViolationsByViolationId(violationId);
        model.addAttribute("violations", violations);
        model.addAttribute("title", "Sent Violations by Violation Type");
        return "sent-violations/list";
    }
} 