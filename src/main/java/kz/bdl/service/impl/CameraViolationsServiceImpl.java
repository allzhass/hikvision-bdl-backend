package kz.bdl.service.impl;

import kz.bdl.converter.CameraViolationConverter;
import kz.bdl.dto.CameraViolationDTO;
import kz.bdl.entity.Camera;
import kz.bdl.entity.CameraViolation;
import kz.bdl.repository.CameraRepository;
import kz.bdl.repository.CameraViolationRepository;
import kz.bdl.service.CameraViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CameraViolationsServiceImpl implements CameraViolationsService {
    @Autowired
    private CameraRepository cameraRepository;
    @Autowired
    private CameraViolationRepository cameraViolationRepository;
    @Autowired
    private CameraViolationConverter cameraViolationConverter;

    @Override
    public List<CameraViolationDTO> getCameraViolationsByAPKId(Long apkId) {
        List<Camera> cameraList = cameraRepository.findByApkId(apkId);

        List<CameraViolationDTO> result = new ArrayList<>();
        for (Camera camera : cameraList) {
            result.addAll(cameraViolationRepository.findByCameraIdOrderByIsSendErapDesc(camera.getId()).stream().map(cameraViolationConverter::toDTO).toList());
        }
        return result;
    }

    @Override
    public List<CameraViolationDTO> getCameraViolationsByCameraId(Long cameraId) {
        return cameraViolationRepository.findByCameraIdOrderByIsSendErapDesc(cameraId).stream().map(cameraViolationConverter::toDTO).toList();
    }

    @Override
    public ResponseEntity<String> updateIsSendErap(Long id, Boolean isSendErap) {
        CameraViolation cameraViolation = cameraViolationRepository.findById(id)
                .orElse(null);
        if (cameraViolation == null) {
            return ResponseEntity.badRequest().body("Camera violation not found");
        }
        cameraViolation.setIsSendErap(isSendErap);
        cameraViolationRepository.save(cameraViolation);
        return ResponseEntity.ok("Status updated successfully");
    }

    @Override
    public ResponseEntity<String> updateIsProd(Long id, Boolean isProd) {
        CameraViolation cameraViolation = cameraViolationRepository.findById(id)
                .orElse(null);
        if (cameraViolation == null) {
            return ResponseEntity.badRequest().body("Camera violation not found");
        }
        cameraViolation.setIsProd(isProd);
        cameraViolationRepository.save(cameraViolation);
        return ResponseEntity.ok("Status updated successfully");
    }
}
