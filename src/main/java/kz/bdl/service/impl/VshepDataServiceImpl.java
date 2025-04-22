package kz.bdl.service.impl;

import kz.bdl.converter.VshepDataConverter;
import kz.bdl.dto.LocationDTO;
import kz.bdl.dto.VshepDataDTO;
import kz.bdl.repository.VshepDataRepository;
import kz.bdl.service.VshepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VshepDataServiceImpl implements VshepDataService {
    @Autowired private VshepDataRepository vshepDataRepository;
    @Autowired private VshepDataConverter vshepDataConverter;

    @Override
    public List<VshepDataDTO> getAllVshepData() {
        return vshepDataRepository.findAll().stream().map(vshepDataConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public VshepDataDTO getVshepDataById(Long id) {
        return vshepDataRepository.findById(id).map(vshepDataConverter::toDTO).orElse(null);
    }

    @Override
    public ResponseEntity<String> addVshepData(VshepDataDTO vshepDataDTO) {
        vshepDataRepository.save(vshepDataConverter.toEntity(vshepDataDTO));
        return ResponseEntity.ok("Vshep Data added successfully");
    }

    @Override
    public ResponseEntity<String> changeVshepData(VshepDataDTO vshepDataDTO) {
        if (!vshepDataRepository.existsById(vshepDataDTO.getId())) return ResponseEntity.badRequest().body("Vshep Data not found");
        return addVshepData(vshepDataDTO);
    }
}
