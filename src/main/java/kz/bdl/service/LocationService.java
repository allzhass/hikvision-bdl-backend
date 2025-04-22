package kz.bdl.service;

import kz.bdl.dto.LocationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocationService {
    LocationDTO getLocationById(Long id);
    List<LocationDTO> getAllLocations();
    List<LocationDTO> getLocationsByRegionId(Long regionId);
    List<LocationDTO> getLocationsHasAPK();
    ResponseEntity<String> addLocation(LocationDTO locationDTO);
    ResponseEntity<String> changeLocation(LocationDTO locationDTO);
}