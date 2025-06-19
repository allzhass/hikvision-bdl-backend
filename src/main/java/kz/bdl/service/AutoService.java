package kz.bdl.service;

import kz.bdl.dto.AutoDto;
import java.util.List;

public interface AutoService {
    List<AutoDto> getAllAutos();
    AutoDto getAutoById(Long id);
    AutoDto createAuto(AutoDto autoDto);
    AutoDto updateAuto(Long id, AutoDto autoDto);
    void deleteAuto(Long id);
} 