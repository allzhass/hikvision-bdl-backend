package kz.bdl.controller;

import kz.bdl.dto.LocationDTO;
import kz.bdl.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/location-view")
@Slf4j
public class LocationViewController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public String getAllLocations(Model model) {
        log.info("getAllLocations start");
        model.addAttribute("locations", locationService.getAllLocations());
        log.info("getAllLocations end");
        return "location/list";
    }

    @GetMapping("/{id}")
    public String getLocationById(@PathVariable Long id, Model model) {
        log.info("getLocationById start: {}", id);
        model.addAttribute("location", locationService.getLocationById(id));
        log.info("getLocationById end");
        return "location/view";
    }

    @GetMapping("/region/{regionId}")
    public String getLocationsByRegionId(@PathVariable Long regionId, Model model) {
        log.info("getLocationsByRegionId start: {}", regionId);
        model.addAttribute("locations", locationService.getLocationsByRegionId(regionId));
        log.info("getLocationsByRegionId end");
        return "location/list";
    }

    @GetMapping("/has-apk")
    public String getLocationsHasAPK(Model model) {
        log.info("getLocationsHasAPK start");
        model.addAttribute("locations", locationService.getLocationsHasAPK());
        log.info("getLocationsHasAPK end");
        return "location/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("location", new LocationDTO());
        log.info("showAddForm end");
        return "location/add";
    }

    @PostMapping("/add")
    public String addLocation(@ModelAttribute LocationDTO locationDTO) {
        log.info("addLocation start: {}", locationDTO);
        locationService.addLocation(locationDTO);
        log.info("addLocation end");
        return "redirect:/location-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("location", locationService.getLocationById(id));
        log.info("showEditForm end");
        return "location/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeLocation(@PathVariable Long id, @ModelAttribute LocationDTO locationDTO) {
        log.info("changeLocation start: {}", id);
        locationDTO.setId(id);
        locationService.changeLocation(locationDTO);
        log.info("changeLocation end");
        return "redirect:/location-view";
    }
} 