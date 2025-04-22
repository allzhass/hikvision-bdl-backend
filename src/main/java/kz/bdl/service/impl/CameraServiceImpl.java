package kz.bdl.service.impl;

import kz.bdl.converter.CameraConverter;
import kz.bdl.dto.CameraDTO;
import kz.bdl.entity.APK;
import kz.bdl.exception.ResourceNotFoundException;
import kz.bdl.repository.APKRepository;
import kz.bdl.repository.CameraRepository;
import kz.bdl.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService {
    @Autowired
    private CameraRepository cameraRepository;
    @Autowired
    private APKRepository apkRepository;
    @Autowired
    private CameraConverter cameraConverter;

    @Override
    public CameraDTO getCameraById(Long id) {
        return cameraRepository.findById(id).map(cameraConverter::toDTO).orElse(null);
    }

    @Override
    public List<CameraDTO> getAllCamera() {
        return cameraRepository.findAll().stream().map(cameraConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CameraDTO> getCameraByAPKId(Long apkId) {
        return cameraRepository.findByApkId(apkId).stream().map(cameraConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addCamera(CameraDTO cameraDTO) {
        APK apk = apkRepository.findById(cameraDTO.getApkId()).orElseThrow(
                () -> new ResourceNotFoundException("APK is not exists with given id: " + cameraDTO.getApkId()));
        if (apk == null) return ResponseEntity.badRequest().body("Invalid APK ID");
        cameraRepository.save(cameraConverter.toEntity(cameraDTO, apk));
        return ResponseEntity.ok("Camera added successfully");
    }

    @Override
    public ResponseEntity<String> changeCamera(CameraDTO cameraDTO) {
        if (!cameraRepository.existsById(cameraDTO.getId())) return ResponseEntity.badRequest().body("Camera not found");
        return addCamera(cameraDTO);
    }
}