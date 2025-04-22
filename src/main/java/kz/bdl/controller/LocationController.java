package kz.bdl.controller;

import kz.bdl.dto.LocationDTO;
import kz.bdl.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public LocationDTO getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/region/{regionId}")
    public List<LocationDTO> getLocationsByRegionId(@PathVariable Long regionId) {
        return locationService.getLocationsByRegionId(regionId);
    }

    @GetMapping("/has-apk")
    public List<LocationDTO> getLocationsHasAPK() {
        return locationService.getLocationsHasAPK();
    }

    @PostMapping
    public ResponseEntity<String> addLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.addLocation(locationDTO);
    }

    @PutMapping
    public ResponseEntity<String> changeLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.changeLocation(locationDTO);
    }
}