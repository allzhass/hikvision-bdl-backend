package kz.bdl.service;

import kz.bdl.dto.SentViolationsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SentViolationsService {
    List<SentViolationsDTO> getAllSentViolations();
    Page<SentViolationsDTO> getPaginatedSentViolations(int page, int size);
    List<SentViolationsDTO> getSentViolationsByCameraName(String cameraName);
    List<SentViolationsDTO> getSentViolationsByCameraId(Long cameraId);
    List<SentViolationsDTO> getSentViolationsByCameraIp(String cameraIp);
    Page<SentViolationsDTO> getPaginatedSentViolationsByCameraName(String cameraName, int page, int size);
    Page<SentViolationsDTO> getPaginatedSentViolationsByCameraIp(String cameraIp, int page, int size);
    SentViolationsDTO getSentViolationById(Long id);
    Page<SentViolationsDTO> getPaginatedSentViolationsByPlateNumber(String plateNumber, int page, int size);
    Page<SentViolationsDTO> getPaginatedSentViolationsByRegionCode(String regionCode, int page, int size);
} 