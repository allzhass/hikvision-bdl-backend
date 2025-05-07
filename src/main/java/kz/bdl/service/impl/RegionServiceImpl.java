package kz.bdl.service.impl;

import kz.bdl.converter.RegionConverter;
import kz.bdl.dto.LocationDTO;
import kz.bdl.dto.RegionDTO;
import kz.bdl.entity.Region;
import kz.bdl.entity.VshepData;
import kz.bdl.repository.RegionRepository;
import kz.bdl.repository.VshepDataRepository;
import kz.bdl.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired private VshepDataRepository vshepDataRepository;
    @Autowired private RegionRepository regionRepository;
    @Autowired private RegionConverter regionConverter;

    @Override
    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream().map(regionConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RegionDTO> getAllRegionsHasLocation() {
        return regionRepository.findAllWithLocations().stream().map(regionConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addRegion(RegionDTO regionDTO) {
        VshepData vshepData = vshepDataRepository.findById(regionDTO.getVshepDataId()).orElse(null);
        if (vshepData == null) return ResponseEntity.badRequest().body("Invalid Vshep Data ID");
        regionRepository.save(regionConverter.toEntity(regionDTO, vshepData));
        return ResponseEntity.ok("Region added successfully");
    }

    @Override
    public RegionDTO getRegionById(Long id) {
        return regionRepository.findById(id).map(regionConverter::toDTO).orElse(null);
    }

    @Override
    public List<RegionDTO> getRegionsByVshepDataId(Long vshepDataId) {
        return regionRepository.findByVshepDataId(vshepDataId).stream().map(regionConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> changeRegion(RegionDTO regionDTO) {
        if (!regionRepository.existsById(regionDTO.getId())) {
            return ResponseEntity.badRequest().body("Region not found");
        }
        VshepData vshepData = vshepDataRepository.findById(regionDTO.getVshepDataId()).orElse(null);
        if (vshepData == null) {
            return ResponseEntity.badRequest().body("Invalid Vshep Data ID");
        }
        regionRepository.save(regionConverter.toEntity(regionDTO, vshepData));
        return ResponseEntity.ok("Region updated successfully");
    }
}
