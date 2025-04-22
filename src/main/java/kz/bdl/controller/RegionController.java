package kz.bdl.controller;

import kz.bdl.dto.RegionDTO;
import kz.bdl.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<RegionDTO> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("/has-location")
    public List<RegionDTO> getAllRegionsHasLocation() {
        return regionService.getAllRegionsHasLocation();
    }

    @PostMapping
    public ResponseEntity<String> addRegion(@RequestBody RegionDTO regionDTO) {
        return regionService.addRegion(regionDTO);
    }
}