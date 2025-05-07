package kz.bdl.controller;

import kz.bdl.dto.LocationDTO;
import kz.bdl.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/location-view")
public class LocationViewController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public String getAllLocations(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        return "location/list";
    }

    @GetMapping("/{id}")
    public String getLocationById(@PathVariable Long id, Model model) {
        model.addAttribute("location", locationService.getLocationById(id));
        return "location/view";
    }

    @GetMapping("/region/{regionId}")
    public String getLocationsByRegionId(@PathVariable Long regionId, Model model) {
        model.addAttribute("locations", locationService.getLocationsByRegionId(regionId));
        return "location/list";
    }

    @GetMapping("/has-apk")
    public String getLocationsHasAPK(Model model) {
        model.addAttribute("locations", locationService.getLocationsHasAPK());
        return "location/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("location", new LocationDTO());
        return "location/add";
    }

    @PostMapping("/add")
    public String addLocation(@ModelAttribute LocationDTO locationDTO) {
        locationService.addLocation(locationDTO);
        return "redirect:/location-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("location", locationService.getLocationById(id));
        return "location/edit";
    }

    @PostMapping("/edit/{id}")
    public String changeLocation(@PathVariable Long id, @ModelAttribute LocationDTO locationDTO) {
        locationDTO.setId(id);
        locationService.changeLocation(locationDTO);
        return "redirect:/location-view";
    }
} 