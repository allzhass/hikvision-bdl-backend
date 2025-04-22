package kz.bdl.service;

import kz.bdl.dto.CameraDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CameraService {
    CameraDTO getCameraById(Long id);
    List<CameraDTO> getAllCamera();
    List<CameraDTO> getCameraByAPKId(Long apkId);
    ResponseEntity<String> addCamera(CameraDTO cameraDTO);
    ResponseEntity<String> changeCamera(CameraDTO cameraDTO);
}