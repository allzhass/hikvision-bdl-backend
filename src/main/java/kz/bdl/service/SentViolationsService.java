package kz.bdl.service;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.dto.CameraDTO;
import kz.bdl.dto.APKDTO;
import kz.bdl.dto.ViolationDTO;
import java.util.List;

public interface SentViolationsService {
    List<SentViolationsDTO> getSentViolationsByCameraId(Long cameraId);
    List<SentViolationsDTO> getSentViolationsByAPKId(Long apkId);
    List<SentViolationsDTO> getSentViolationsByViolationId(Long violationId);
    List<SentViolationsDTO> getAllSentViolations();
} 