package kz.bdl.repository;

import kz.bdl.entity.CameraViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraViolationRepository extends JpaRepository<CameraViolation, Long> {
    List<CameraViolation> findByCameraIdOrderByIsSendErapDesc(Long cameraId);
}