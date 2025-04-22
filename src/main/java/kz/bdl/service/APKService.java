package kz.bdl.service;

import kz.bdl.dto.APKDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface APKService {
    APKDTO getAPKById(Long id);
    List<APKDTO> getAllAPK();
    List<APKDTO> getAPKByLocationId(Long locationId);
    List<APKDTO> getAPKHasCamera();
    ResponseEntity<String> addAPK(APKDTO apkDTO);
    ResponseEntity<String> changeAPK(APKDTO apkDTO);
}