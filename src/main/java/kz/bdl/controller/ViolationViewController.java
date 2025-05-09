package kz.bdl.controller;

import kz.bdl.dto.CameraDTO;
import kz.bdl.dto.ViolationDTO;
import kz.bdl.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/violation-view")
public class ViolationViewController {
    @Autowired
    private ViolationService violationService;

    @GetMapping
    public String getAllViolations(Model model) {
        model.addAttribute("violations", violationService.getAllViolations());
        return "violation/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("violation", new ViolationDTO());
        return "violation/add";
    }

    @PostMapping("/add")
    public String addViolation(@ModelAttribute ViolationDTO violationDTO) {
        violationService.addViolation(violationDTO);
        return "redirect:/violation-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("violation", violationService.getViolationById(id));
        return "violation/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeViolation(@PathVariable Long id, @ModelAttribute ViolationDTO violationDTO) {
        violationDTO.setId(id);
        violationService.changeViolation(violationDTO);
        return "redirect:/violation-view";
    }
} 