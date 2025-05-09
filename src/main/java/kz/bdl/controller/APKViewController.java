package kz.bdl.controller;

import kz.bdl.dto.APKDTO;
import kz.bdl.service.APKService;
import kz.bdl.service.CameraViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apk-view")
public class APKViewController {
    @Autowired
    private APKService apkService;

    @GetMapping
    public String getAllAPK(Model model) {
        model.addAttribute("apks", apkService.getAllAPK());
        return "apk/list";
    }

    @GetMapping("/{id}")
    public String getAPKById(@PathVariable Long id, Model model) {
        model.addAttribute("apk", apkService.getAPKById(id));
        return "apk/view";
    }

    @GetMapping("/location/{locationId}")
    public String getAPKByLocationId(@PathVariable Long locationId, Model model) {
        model.addAttribute("apks", apkService.getAPKByLocationId(locationId));
        return "apk/list";
    }

    @GetMapping("/has-camera")
    public String getAPKHasCamera(Model model) {
        model.addAttribute("apks", apkService.getAPKHasCamera());
        return "apk/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("apk", new APKDTO());
        return "apk/add";
    }

    @PostMapping("/add")
    public String addAPK(@ModelAttribute APKDTO apkDTO) {
        apkService.addAPK(apkDTO);
        return "redirect:/apk-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("apk", apkService.getAPKById(id));
        return "apk/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeAPK(@PathVariable Long id, @ModelAttribute APKDTO apkDTO) {
        apkDTO.setId(id);
        apkService.changeAPK(apkDTO);
        return "redirect:/apk-view";
    }
} 