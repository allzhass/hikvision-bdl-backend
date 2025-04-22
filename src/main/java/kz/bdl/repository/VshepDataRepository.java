package kz.bdl.repository;

import kz.bdl.entity.VshepData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VshepDataRepository extends JpaRepository<VshepData, Long> {}
