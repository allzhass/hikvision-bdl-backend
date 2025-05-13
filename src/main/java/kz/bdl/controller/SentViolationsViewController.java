package kz.bdl.controller;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.service.SentViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            @RequestParam(value = "name", required = false) String cameraName,
            @RequestParam(value = "ip", required = false) String cameraIp,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        if (cameraIp != null && !cameraIp.isEmpty()) {
            Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolationsByCameraIp(cameraIp, page, size);
            model.addAttribute("violations", violationsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", violationsPage.getTotalPages());
            model.addAttribute("title", "Sent Violations by Camera IP");
            model.addAttribute("cameraIp", cameraIp);
        } else if (cameraName != null && !cameraName.isEmpty()) {
            Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolationsByCameraName(cameraName, page, size);
            model.addAttribute("violations", violationsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", violationsPage.getTotalPages());
            model.addAttribute("title", "Sent Violations by Camera Name");
            model.addAttribute("cameraName", cameraName);
        } else {
            return "redirect:/sent-violations-view/paginated";
        }
        return "sent-violations/paginated-list";
    }

    @GetMapping("/paginated")
    public String getPaginatedList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolations(page, size);
        
        model.addAttribute("violations", violationsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", violationsPage.getTotalPages());
        model.addAttribute("title", "Sent Violations (Paginated)");
        
        return "sent-violations/paginated-list";
    }
}