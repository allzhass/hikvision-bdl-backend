package kz.bdl.controller;

import kz.bdl.dto.LocationDTO;
import kz.bdl.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@Slf4j
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public LocationDTO getLocationById(@PathVariable Long id) {
        log.info("getLocationById start: {}", id);
        LocationDTO result = locationService.getLocationById(id);
        log.info("getLocationById end");
        return result;
    }

    @GetMapping
    public List<LocationDTO> getAllLocations() {
        log.info("getAllLocations start");
        List<LocationDTO> result = locationService.getAllLocations();
        log.info("getAllLocations end");
        return result;
    }

    @GetMapping("/region/{regionId}")
    public List<LocationDTO> getLocationsByRegionId(@PathVariable Long regionId) {
        log.info("getLocationsByRegionId start: {}", regionId);
        List<LocationDTO> result = locationService.getLocationsByRegionId(regionId);
        log.info("getLocationsByRegionId end");
        return result;
    }

    @GetMapping("/has-apk")
    public List<LocationDTO> getLocationsHasAPK() {
        log.info("getLocationsHasAPK start");
        List<LocationDTO> result = locationService.getLocationsHasAPK();
        log.info("getLocationsHasAPK end");
        return result;
    }

    @PostMapping
    public ResponseEntity<String> addLocation(@RequestBody LocationDTO locationDTO) {
        log.info("addLocation start: {}", locationDTO);
        ResponseEntity<String> result = locationService.addLocation(locationDTO);
        log.info("addLocation end");
        return result;
    }

    @PutMapping
    public ResponseEntity<String> changeLocation(@RequestBody LocationDTO locationDTO) {
        log.info("changeLocation start: {}", locationDTO);
        ResponseEntity<String> result = locationService.changeLocation(locationDTO);
        log.info("changeLocation end");
        return result;
    }
}