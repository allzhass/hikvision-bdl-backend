package kz.bdl.controller;

import kz.bdl.dto.APKDTO;
import kz.bdl.service.APKService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apk-view")
@Slf4j
public class APKViewController {
    @Autowired
    private APKService apkService;

    @GetMapping
    public String getAllAPK(Model model) {
        log.info("getAllAPK start");
        model.addAttribute("apks", apkService.getAllAPK());
        log.info("getAllAPK end");
        return "apk/list";
    }

    @GetMapping("/{id}")
    public String getAPKById(@PathVariable Long id, Model model) {
        log.info("getAPKById start: {}", id);
        model.addAttribute("apk", apkService.getAPKById(id));
        log.info("getAPKById end");
        return "apk/view";
    }

    @GetMapping("/location/{locationId}")
    public String getAPKByLocationId(@PathVariable Long locationId, Model model) {
        log.info("getAPKByLocationId start: {}", locationId);
        model.addAttribute("apks", apkService.getAPKByLocationId(locationId));
        log.info("getAPKByLocationId end");
        return "apk/list";
    }

    @GetMapping("/has-camera")
    public String getAPKHasCamera(Model model) {
        log.info("getAPKHasCamera start");
        model.addAttribute("apks", apkService.getAPKHasCamera());
        log.info("getAPKHasCamera end");
        return "apk/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("apk", new APKDTO());
        log.info("showAddForm end");
        return "apk/add";
    }

    @PostMapping("/add")
    public String addAPK(@ModelAttribute APKDTO apkDTO) {
        log.info("addAPK start: {}", apkDTO);
        apkService.addAPK(apkDTO);
        log.info("addAPK end");
        return "redirect:/apk-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("apk", apkService.getAPKById(id));
        log.info("showEditForm end");
        return "apk/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeAPK(@PathVariable Long id, @ModelAttribute APKDTO apkDTO) {
        log.info("changeAPK start: {}", id);
        apkDTO.setId(id);
        apkService.changeAPK(apkDTO);
        log.info("changeAPK end");
        return "redirect:/apk-view";
    }
} 