package kz.bdl.service.impl;

import kz.bdl.converter.APKConverter;
import kz.bdl.dto.APKDTO;
import kz.bdl.entity.Location;
import kz.bdl.repository.APKRepository;
import kz.bdl.repository.LocationRepository;
import kz.bdl.service.APKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class APKServiceImpl implements APKService {
    @Autowired
    private APKRepository apkRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private APKConverter apkConverter;

    @Override
    public APKDTO getAPKById(Long id) {
        return apkRepository.findById(id).map(apkConverter::toDTO).orElse(null);
    }

    @Override
    public List<APKDTO> getAllAPK() {
        return apkRepository.findAll(org.springframework.data.domain.Sort.by("certExpiry")).stream().map(apkConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<APKDTO> getAPKByLocationId(Long locationId) {
        return apkRepository.findByLocationId(locationId).stream().map(apkConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<APKDTO> getAPKHasCamera() {
        return apkRepository.findAllWithCameras().stream().map(apkConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addAPK(APKDTO apkDTO) {
        Location location = locationRepository.findById(apkDTO.getLocationId()).orElse(null);
        if (location == null) return ResponseEntity.badRequest().body("Invalid Location ID");
        apkRepository.save(apkConverter.toEntity(apkDTO, location));
        return ResponseEntity.ok("APK added successfully");
    }

    @Override
    public ResponseEntity<String> changeAPK(APKDTO apkDTO) {
        if (!apkRepository.existsById(apkDTO.getId())) return ResponseEntity.badRequest().body("APK not found");
        return addAPK(apkDTO);
    }
}