package kz.bdl.service.impl;

import kz.bdl.converter.LocationConverter;
import kz.bdl.dto.LocationDTO;
import kz.bdl.entity.Region;
import kz.bdl.repository.LocationRepository;
import kz.bdl.repository.RegionRepository;
import kz.bdl.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired private LocationRepository locationRepository;
    @Autowired private RegionRepository regionRepository;
    @Autowired private LocationConverter locationConverter;

    @Override
    public LocationDTO getLocationById(Long id) {
        return locationRepository.findById(id).map(locationConverter::toDTO).orElse(null);
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream().map(locationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getLocationsByRegionId(Long regionId) {
        return locationRepository.findByRegionId(regionId).stream().map(locationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getLocationsHasAPK() {
        return locationRepository.findAllWithApks().stream().map(locationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addLocation(LocationDTO locationDTO) {
        Region region = regionRepository.findById(locationDTO.getRegionId()).orElse(null);
        if (region == null) return ResponseEntity.badRequest().body("Invalid Region ID");
        locationRepository.save(locationConverter.toEntity(locationDTO, region));
        return ResponseEntity.ok("Location added successfully");
    }

    @Override
    public ResponseEntity<String> changeLocation(LocationDTO locationDTO) {
        if (!locationRepository.existsById(locationDTO.getId())) return ResponseEntity.badRequest().body("Location not found");
        return addLocation(locationDTO);
    }
}