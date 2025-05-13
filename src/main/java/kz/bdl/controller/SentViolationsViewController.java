package kz.bdl.controller;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.service.SentViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/camera/search")
    public String getSentViolationsByCameraId(
            @RequestParam(value = "id", required = false) Long cameraId,
            @RequestParam(value = "name", required = false) String cameraName,
            @RequestParam(value = "ip", required = false) String cameraIp,
            Model model) {

        List<SentViolationsDTO> violations;
        if (cameraId != null) {
            System.out.println("ID: " + cameraId);
            violations = sentViolationsService.getSentViolationsByCameraId(cameraId);
            model.addAttribute("violations", violations);
            model.addAttribute("title", "Sent Violations by Camera");

        } else if (cameraIp != null && !cameraIp.isEmpty()) {
            System.out.println("IP: " + cameraIp);
            violations = sentViolationsService.getSentViolationsByCameraIp(cameraIp);
            model.addAttribute("violations", violations);
            model.addAttribute("title", "Sent Violations by Camera IP");

        } else if (cameraName != null && !cameraName.isEmpty()) {
            System.out.println("NAME: " + cameraName);
            violations = sentViolationsService.getSentViolationsByCameraName(cameraName);
            model.addAttribute("violations", violations);
            model.addAttribute("title", "Sent Violations by Camera Name");
        }
        return "sent-violations/list";
    }
} 