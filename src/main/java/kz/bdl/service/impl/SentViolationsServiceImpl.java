package kz.bdl.service.impl;

import kz.bdl.converter.SentViolationsConverter;
import kz.bdl.dto.APKDTO;
import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.repository.SentViolationsRepository;
import kz.bdl.service.SentViolationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SentViolationsServiceImpl implements SentViolationsService {
    @Autowired
    private SentViolationsRepository sentViolationsRepository;
    @Autowired
    private SentViolationsConverter sentViolationsConverter;

    @Override
    @Transactional
    public List<SentViolationsDTO> getAllSentViolations() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt"));
        return sentViolationsRepository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Page<SentViolationsDTO> getPaginatedSentViolations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return sentViolationsRepository.findAllWithPagination(pageable)
                .map(sentViolationsConverter::toDTO);
    }

    @Override
    @Transactional
    public Page<SentViolationsDTO> getPaginatedSentViolationsByCameraName(String cameraName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return sentViolationsRepository.findByCameraNameWithPagination(cameraName, pageable)
                .map(sentViolationsConverter::toDTO);
    }

    @Override
    @Transactional
    public Page<SentViolationsDTO> getPaginatedSentViolationsByCameraIp(String cameraIp, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return sentViolationsRepository.findByCameraIpWithPagination(cameraIp, pageable)
                .map(sentViolationsConverter::toDTO);
    }

    @Override
    @Transactional
    public Page<SentViolationsDTO> getPaginatedSentViolationsByPlateNumber(String plateNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return sentViolationsRepository.findByPlateNumberWithPagination(plateNumber, pageable)
                .map(sentViolationsConverter::toDTO);
    }

    @Override
    public SentViolationsDTO getSentViolationById(Long id) {
        return sentViolationsRepository.findById(id).map(sentViolationsConverter::toDTO).orElse(null);
    }

    @Override
    @Transactional
    public List<SentViolationsDTO> getSentViolationsByCameraName(String cameraName) {
        return sentViolationsRepository.findByCameraViolation_Camera_Name(cameraName)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SentViolationsDTO> getSentViolationsByCameraId(Long cameraId) {
        return sentViolationsRepository.findByCameraViolation_Camera_Id(cameraId)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SentViolationsDTO> getSentViolationsByCameraIp(String cameraIp) {
        return sentViolationsRepository.findByCameraViolation_Camera_Ip(cameraIp)
                .stream()
                .map(sentViolationsConverter::toDTO)
                .collect(Collectors.toList());
    }
} 