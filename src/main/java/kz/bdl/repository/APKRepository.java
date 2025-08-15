package kz.bdl.repository;

import kz.bdl.entity.APK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface APKRepository extends JpaRepository<APK, Long> {
    List<APK> findByLocationId(Long locationId);

    @Query("SELECT a FROM APK a WHERE EXISTS (SELECT 1 FROM Camera с WHERE с.apk.id = a.id)")
    List<APK> findAllWithCameras();
    
    APK findByDeviceNumber(String deviceNumber);
}
