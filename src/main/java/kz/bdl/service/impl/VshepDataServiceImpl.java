package kz.bdl.service.impl;

import kz.bdl.converter.VshepDataConverter;
import kz.bdl.dto.VshepDataDTO;
import kz.bdl.entity.CertificateData;
import kz.bdl.entity.VshepData;
import kz.bdl.repository.CertificateDataRepository;
import kz.bdl.repository.VshepDataRepository;
import kz.bdl.service.VshepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VshepDataServiceImpl implements VshepDataService {
    @Autowired private VshepDataRepository vshepDataRepository;
    @Autowired private CertificateDataRepository certificateDataRepository;
    @Autowired private VshepDataConverter vshepDataConverter;

    @Override
    public List<VshepDataDTO> getAllVshepData() {
        return vshepDataRepository.findAll().stream().map(vshepDataConverter::toDTOShort).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VshepDataDTO getVshepDataById(Long id) {
        return vshepDataRepository.findById(id).map(vshepDataConverter::toDTO).orElse(null);
    }

    @Override
    @Transactional
    public ResponseEntity<String> addVshepData(VshepDataDTO vshepDataDTO) {
        VshepData entity = vshepDataConverter.toEntity(vshepDataDTO);
        if (vshepDataDTO.getCertId() != null) {
            CertificateData cert = certificateDataRepository.findById(vshepDataDTO.getCertId())
                    .orElseThrow(() -> new RuntimeException("Certificate not found"));
            entity.setCertId(cert);
        }
        vshepDataRepository.save(entity);
        return ResponseEntity.ok("Vshep Data added successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<String> changeVshepData(VshepDataDTO vshepDataDTO) {
        if (!vshepDataRepository.existsById(vshepDataDTO.getId())) {
            return ResponseEntity.badRequest().body("Vshep Data not found");
        }
        
        VshepData entity = vshepDataConverter.toEntity(vshepDataDTO);
        if (vshepDataDTO.getCertId() != null) {
            CertificateData cert = certificateDataRepository.findById(vshepDataDTO.getCertId())
                    .orElseThrow(() -> new RuntimeException("Certificate not found"));
            entity.setCertId(cert);
        }
        vshepDataRepository.save(entity);
        return ResponseEntity.ok("Vshep Data updated successfully");
    }
}
