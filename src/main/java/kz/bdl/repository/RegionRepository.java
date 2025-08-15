package kz.bdl.repository;

import kz.bdl.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("SELECT r FROM Region r WHERE EXISTS (SELECT 1 FROM Location l WHERE l.region.id = r.id)")
    List<Region> findAllWithLocations();

    List<Region> findByVshepDataId(Long vshepDataId);
    
    Region findByCode(String code);
}