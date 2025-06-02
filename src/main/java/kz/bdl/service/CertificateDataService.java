package kz.bdl.service;

import kz.bdl.entity.CertificateData;
import kz.bdl.repository.CertificateDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateDataService {

    private final CertificateDataRepository certificateDataRepository;

    @Autowired
    public CertificateDataService(CertificateDataRepository certificateDataRepository) {
        this.certificateDataRepository = certificateDataRepository;
    }

    public List<CertificateData> findAll() {
        return certificateDataRepository.findAll();
    }

    public Optional<CertificateData> findById(Long id) {
        return certificateDataRepository.findById(id);
    }

    public CertificateData save(CertificateData certificateData) {
        return certificateDataRepository.save(certificateData);
    }

    public void deleteById(Long id) {
        certificateDataRepository.deleteById(id);
    }
} 