package kz.bdl.service.impl;

import kz.bdl.converter.SentViolationsConverter;
import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.repository.SentViolationsRepository;
import kz.bdl.service.SentViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SentViolationsServiceImpl implements SentViolationsService {
    @Autowired
    private SentViolationsRepository sentViolationsRepository;
    @Autowired
    private SentViolationsConverter sentViolationsConverter;

    @Override
    public List<SentViolationsDTO> getSentViolationsByCameraId(Long cameraId) {
        return sentViolationsRepository.findByCameraViolation_Camera_Id(cameraId)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SentViolationsDTO> getSentViolationsByAPKId(Long apkId) {
        return sentViolationsRepository.findByCameraViolation_Camera_Apk_Id(apkId)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SentViolationsDTO> getSentViolationsByViolationId(Long violationId) {
        return sentViolationsRepository.findByCameraViolation_Violation_Id(violationId)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SentViolationsDTO> getAllSentViolations() {
        return sentViolationsRepository.findAll()
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }
} 