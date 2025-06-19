package kz.bdl.service.impl;

import kz.bdl.dto.AutoDto;
import kz.bdl.entity.Auto;
import kz.bdl.repository.AutoRepository;
import kz.bdl.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoServiceImpl implements AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public List<AutoDto> getAllAutos() {
        return autoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AutoDto getAutoById(Long id) {
        return autoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Auto not found with id: " + id));
    }

    @Override
    public AutoDto createAuto(AutoDto autoDto) {
        Auto auto = convertToEntity(autoDto);
        Auto savedAuto = autoRepository.save(auto);
        return convertToDto(savedAuto);
    }

    @Override
    public AutoDto updateAuto(Long id, AutoDto autoDto) {
        Auto existingAuto = autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto not found with id: " + id));
        
        existingAuto.setPlateNumber(autoDto.getPlateNumber());
        existingAuto.setDescription(autoDto.getDescription());
        existingAuto.setIsSendViolation(autoDto.getIsSendViolation());
        
        Auto updatedAuto = autoRepository.save(existingAuto);
        return convertToDto(updatedAuto);
    }

    @Override
    public void deleteAuto(Long id) {
        if (!autoRepository.existsById(id)) {
            throw new RuntimeException("Auto not found with id: " + id);
        }
        autoRepository.deleteById(id);
    }

    private AutoDto convertToDto(Auto auto) {
        return new AutoDto(
                auto.getId(),
                auto.getPlateNumber(),
                auto.getDescription(),
                auto.getIsSendViolation()
        );
    }

    private Auto convertToEntity(AutoDto autoDto) {
        Auto auto = new Auto();
        auto.setId(autoDto.getId());
        auto.setPlateNumber(autoDto.getPlateNumber());
        auto.setDescription(autoDto.getDescription());
        auto.setIsSendViolation(autoDto.getIsSendViolation());
        return auto;
    }
} 