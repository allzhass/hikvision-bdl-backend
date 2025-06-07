package kz.bdl.controller;

import kz.bdl.dto.CameraDTO;
import kz.bdl.service.CameraService;
import kz.bdl.service.CameraViolationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/camera-view")
@Slf4j
public class CameraViewController {
    @Autowired
    private CameraService cameraService;
    @Autowired
    private CameraViolationsService cameraViolationsService;

    @GetMapping
    public String getAllCamera(Model model) {
        log.info("getAllCamera start");
        model.addAttribute("cameras", cameraService.getAllCamera());
        log.info("getAllCamera end");
        return "camera/list";
    }

    @GetMapping("/{id}")
    public String getCameraById(@PathVariable Long id, Model model) {
        log.info("getCameraById start: {}", id);
        model.addAttribute("camera", cameraService.getCameraById(id));
        model.addAttribute("violations", cameraViolationsService.getCameraViolationsByCameraId(id));
        log.info("getCameraById end");
        return "camera/view";
    }

    @GetMapping("/apk/{apkId}")
    public String getCameraByAPKId(@PathVariable Long apkId, Model model) {
        log.info("getCameraByAPKId start: {}", apkId);
        model.addAttribute("cameras", cameraService.getCameraByAPKId(apkId));
        log.info("getCameraByAPKId end");
        return "camera/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("camera", new CameraDTO());
        log.info("showAddForm end");
        return "camera/add";
    }

    @PostMapping("/add")
    public String addCamera(@ModelAttribute CameraDTO cameraDTO) {
        log.info("addCamera start: {}", cameraDTO);
        cameraService.addCamera(cameraDTO);
        log.info("addCamera end");
        return "redirect:/camera-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("camera", cameraService.getCameraById(id));
        log.info("showEditForm end");
        return "camera/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeCamera(@PathVariable Long id, @ModelAttribute CameraDTO cameraDTO) {
        log.info("changeCamera start: {}", id);
        cameraDTO.setId(id);
        cameraService.changeCamera(cameraDTO);
        log.info("changeCamera end");
        return "redirect:/camera-view";
    }

    @PostMapping("/violation/{id}/toggle-erap")
    @ResponseBody
    public ResponseEntity<String> toggleIsSendErap(@PathVariable Long id, @RequestParam Boolean isSendErap) {
        log.info("toggleIsSendErap start: id={}, isSendErap={}", id, isSendErap);
        ResponseEntity<String> result = cameraViolationsService.updateIsSendErap(id, isSendErap);
        log.info("toggleIsSendErap end");
        return result;
    }

    @PostMapping("/violation/{id}/toggle-prod")
    @ResponseBody
    public ResponseEntity<String> toggleIsProd(@PathVariable Long id, @RequestParam Boolean isProd) {
        log.info("toggleIsProd start: id={}, isProd={}", id, isProd);
        ResponseEntity<String> result = cameraViolationsService.updateIsProd(id, isProd);
        log.info("toggleIsProd end");
        return result;
    }
} 