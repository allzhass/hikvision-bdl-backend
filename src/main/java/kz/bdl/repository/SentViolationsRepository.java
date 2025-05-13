package kz.bdl.repository;

import kz.bdl.entity.SentViolations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentViolationsRepository extends JpaRepository<SentViolations, Long> {
    Page<SentViolations> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT s FROM SentViolations s WHERE s.cameraViolation.camera.id = :cameraId")
    List<SentViolations> findByCameraViolation_Camera_Id(@Param("cameraId") Long cameraId);

    @Query("SELECT s FROM SentViolations s WHERE s.cameraViolation.camera.name = :cameraName")
    List<SentViolations> findByCameraViolation_Camera_Name(@Param("cameraName") String cameraName);

    @Query("SELECT s FROM SentViolations s WHERE s.cameraViolation.camera.ip = :cameraIp")
    List<SentViolations> findByCameraViolation_Camera_Ip(@Param("cameraIp") String cameraIp);

    @Query("SELECT s FROM SentViolations s ORDER BY s.createdAt DESC")
    Page<SentViolations> findAllWithPagination(Pageable pageable);

    @Query("SELECT s FROM SentViolations s WHERE s.cameraViolation.camera.name = :cameraName ORDER BY s.createdAt DESC")
    Page<SentViolations> findByCameraNameWithPagination(@Param("cameraName") String cameraName, Pageable pageable);

    @Query("SELECT s FROM SentViolations s WHERE s.cameraViolation.camera.ip = :cameraIp ORDER BY s.createdAt DESC")
    Page<SentViolations> findByCameraIpWithPagination(@Param("cameraIp") String cameraIp, Pageable pageable);
} 