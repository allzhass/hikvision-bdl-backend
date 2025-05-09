package kz.bdl.service;

import kz.bdl.dto.CameraDTO;
import kz.bdl.dto.CameraViolationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CameraViolationsService {
    List<CameraViolationDTO> getCameraViolationsByAPKId(Long apkId);
    List<CameraViolationDTO> getCameraViolationsByCameraId(Long cameraId);
    ResponseEntity<String> updateIsSendErap(Long id, Boolean isSendErap);
}
