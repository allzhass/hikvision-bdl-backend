package kz.bdl.controller;

import kz.bdl.dto.CameraDTO;
import kz.bdl.service.CameraService;
import kz.bdl.service.CameraViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/camera-view")
public class CameraViewController {
    @Autowired
    private CameraService cameraService;
    @Autowired
    private CameraViolationsService cameraViolationsService;

    @GetMapping
    public String getAllCamera(Model model) {
        model.addAttribute("cameras", cameraService.getAllCamera());
        return "camera/list";
    }

    @GetMapping("/{id}")
    public String getCameraById(@PathVariable Long id, Model model) {
        model.addAttribute("camera", cameraService.getCameraById(id));
        model.addAttribute("violations", cameraViolationsService.getCameraViolationsByCameraId(id));
        return "camera/view";
    }

    @GetMapping("/apk/{apkId}")
    public String getCameraByAPKId(@PathVariable Long apkId, Model model) {
        model.addAttribute("cameras", cameraService.getCameraByAPKId(apkId));
        return "camera/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("camera", new CameraDTO());
        return "camera/add";
    }

    @PostMapping("/add")
    public String addCamera(@ModelAttribute CameraDTO cameraDTO) {
        cameraService.addCamera(cameraDTO);
        return "redirect:/camera-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("camera", cameraService.getCameraById(id));
        return "camera/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeCamera(@PathVariable Long id, @ModelAttribute CameraDTO cameraDTO) {
        cameraDTO.setId(id);
        cameraService.changeCamera(cameraDTO);
        return "redirect:/camera-view";
    }

    @PostMapping("/violation/{id}/toggle-erap")
    @ResponseBody
    public ResponseEntity<String> toggleIsSendErap(@PathVariable Long id, @RequestParam Boolean isSendErap) {
        return cameraViolationsService.updateIsSendErap(id, isSendErap);
    }

    @PostMapping("/violation/{id}/toggle-prod")
    @ResponseBody
    public ResponseEntity<String> toggleIsProd(@PathVariable Long id, @RequestParam Boolean isProd) {
        return cameraViolationsService.updateIsProd(id, isProd);
    }
} 