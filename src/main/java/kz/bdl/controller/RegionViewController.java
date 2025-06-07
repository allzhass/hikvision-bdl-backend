package kz.bdl.controller;

import kz.bdl.dto.RegionDTO;
import kz.bdl.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/region-view")
@Slf4j
public class RegionViewController {
    @Autowired
    private RegionService regionService;

    @GetMapping
    public String getAllRegions(Model model) {
        log.info("getAllRegions start");
        model.addAttribute("regions", regionService.getAllRegions());
        log.info("getAllRegions end");
        return "region/list";
    }

    @GetMapping("/has-location")
    public String getAllRegionsHasLocation(Model model) {
        log.info("getAllRegionsHasLocation start");
        model.addAttribute("regions", regionService.getAllRegionsHasLocation());
        log.info("getAllRegionsHasLocation end");
        return "region/list";
    }

    @GetMapping("/{id}")
    public String getRegionById(@PathVariable Long id, Model model) {
        log.info("getRegionById start: {}", id);
        model.addAttribute("region", regionService.getRegionById(id));
        log.info("getRegionById end");
        return "region/view";
    }

    @GetMapping("/vshep-data/{vshepDataId}")
    public String getRegionsByVshepDataId(@PathVariable Long vshepDataId, Model model) {
        log.info("getRegionsByVshepDataId start: {}", vshepDataId);
        model.addAttribute("regions", regionService.getRegionsByVshepDataId(vshepDataId));
        log.info("getRegionsByVshepDataId end");
        return "region/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("region", new RegionDTO());
        log.info("showAddForm end");
        return "region/add";
    }

    @PostMapping("/add")
    public String addRegion(@ModelAttribute RegionDTO regionDTO) {
        log.info("addRegion start: {}", regionDTO);
        regionService.addRegion(regionDTO);
        log.info("addRegion end");
        return "redirect:/region-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("region", regionService.getRegionById(id));
        log.info("showEditForm end");
        return "region/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeRegion(@PathVariable Long id, @ModelAttribute RegionDTO regionDTO) {
        log.info("changeRegion start: {}", id);
        regionDTO.setId(id);
        regionService.changeRegion(regionDTO);
        log.info("changeRegion end");
        return "redirect:/region-view";
    }
} 