package kz.bdl.repository;

import kz.bdl.entity.SentViolations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentViolationsRepository extends JpaRepository<SentViolations, Long> {
    List<SentViolations> findByCameraViolation_Camera_Id(Long cameraId);
    List<SentViolations> findByCameraViolation_Camera_Apk_Id(Long apkId);
    List<SentViolations> findByCameraViolation_Violation_Id(Long violationId);
} 