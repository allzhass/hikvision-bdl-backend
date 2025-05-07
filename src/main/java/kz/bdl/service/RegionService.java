package kz.bdl.service;

import kz.bdl.dto.RegionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RegionService {
    List<RegionDTO> getAllRegions();
    List<RegionDTO> getAllRegionsHasLocation();
    ResponseEntity<String> addRegion(RegionDTO regionDTO);
    RegionDTO getRegionById(Long id);
    List<RegionDTO> getRegionsByVshepDataId(Long vshepDataId);
    ResponseEntity<String> changeRegion(RegionDTO regionDTO);
}