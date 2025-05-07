package kz.bdl.controller;

import kz.bdl.dto.RegionDTO;
import kz.bdl.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/region-view")
public class RegionViewController {
    @Autowired
    private RegionService regionService;

    @GetMapping
    public String getAllRegions(Model model) {
        model.addAttribute("regions", regionService.getAllRegions());
        return "region/list";
    }

    @GetMapping("/has-location")
    public String getAllRegionsHasLocation(Model model) {
        model.addAttribute("regions", regionService.getAllRegionsHasLocation());
        return "region/list";
    }

    @GetMapping("/{id}")
    public String getRegionById(@PathVariable Long id, Model model) {
        model.addAttribute("region", regionService.getRegionById(id));
        return "region/view";
    }

    @GetMapping("/vshep-data/{vshepDataId}")
    public String getRegionsByVshepDataId(@PathVariable Long vshepDataId, Model model) {
        model.addAttribute("regions", regionService.getRegionsByVshepDataId(vshepDataId));
        return "region/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("region", new RegionDTO());
        return "region/add";
    }

    @PostMapping("/add")
    public String addRegion(@ModelAttribute RegionDTO regionDTO) {
        regionService.addRegion(regionDTO);
        return "redirect:/region-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("region", regionService.getRegionById(id));
        return "region/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeRegion(@PathVariable Long id, @ModelAttribute RegionDTO regionDTO) {
        regionDTO.setId(id);
        regionService.changeRegion(regionDTO);
        return "redirect:/region-view";
    }
} 