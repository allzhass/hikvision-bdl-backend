package kz.bdl.repository;

import kz.bdl.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByRegionId(Long regionId);

    @Query("SELECT l FROM Location l WHERE EXISTS (SELECT 1 FROM APK a WHERE a.location.id = l.id)")
    List<Location> findAllWithApks();
    
    Location findByNameRuAndRegionId(String nameRu, Long regionId);
}