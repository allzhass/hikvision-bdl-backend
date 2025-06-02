package kz.bdl.repository;

import kz.bdl.entity.CertificateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateDataRepository extends JpaRepository<CertificateData, Long> {
} 