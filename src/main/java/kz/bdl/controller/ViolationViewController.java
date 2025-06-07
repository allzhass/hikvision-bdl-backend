package kz.bdl.controller;

import kz.bdl.dto.CameraDTO;
import kz.bdl.dto.ViolationDTO;
import kz.bdl.service.ViolationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/violation-view")
@Slf4j
public class ViolationViewController {
    @Autowired
    private ViolationService violationService;

    @GetMapping
    public String getAllViolations(Model model) {
        log.info("getAllViolations start");
        model.addAttribute("violations", violationService.getAllViolations());
        log.info("getAllViolations end");
        return "violation/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("violation", new ViolationDTO());
        log.info("showAddForm end");
        return "violation/add";
    }

    @PostMapping("/add")
    public String addViolation(@ModelAttribute ViolationDTO violationDTO) {
        log.info("addViolation start: {}", violationDTO);
        violationService.addViolation(violationDTO);
        log.info("addViolation end");
        return "redirect:/violation-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("violation", violationService.getViolationById(id));
        log.info("showEditForm end");
        return "violation/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeViolation(@PathVariable Long id, @ModelAttribute ViolationDTO violationDTO) {
        log.info("changeViolation start: {}", id);
        violationDTO.setId(id);
        violationService.changeViolation(violationDTO);
        log.info("changeViolation end");
        return "redirect:/violation-view";
    }
} 