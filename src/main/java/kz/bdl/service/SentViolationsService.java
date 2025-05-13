package kz.bdl.service;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.dto.CameraDTO;
import kz.bdl.dto.APKDTO;
import kz.bdl.dto.ViolationDTO;
import java.util.List;

public interface SentViolationsService {
    List<SentViolationsDTO> getAllSentViolations();
    List<SentViolationsDTO> getSentViolationsByCameraId(Long cameraId);
    List<SentViolationsDTO> getSentViolationsByCameraName(String cameraName);
    List<SentViolationsDTO> getSentViolationsByCameraIp(String cameraIp);
} 