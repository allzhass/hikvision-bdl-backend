package kz.bdl.service;

import kz.bdl.dto.VshepDataDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VshepDataService {
    List<VshepDataDTO> getAllVshepData();
    VshepDataDTO getVshepDataById(Long id);
    ResponseEntity<String> addVshepData(VshepDataDTO vshepDataDTO);
    ResponseEntity<String> changeVshepData(VshepDataDTO vshepDataDTO);
}
