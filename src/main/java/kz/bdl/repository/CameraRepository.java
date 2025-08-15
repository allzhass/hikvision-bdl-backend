package kz.bdl.repository;

import kz.bdl.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
    List<Camera> findByApkId(Long apkId);
    
    Camera findByCode(String code);
    
    Camera findByIp(String ip);
}
