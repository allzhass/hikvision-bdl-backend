package kz.bdl.converter;

import kz.bdl.dto.RegionDTO;
import kz.bdl.entity.Region;
import kz.bdl.entity.VshepData;
import org.springframework.stereotype.Component;

@Component
public class RegionConverter {
    public RegionDTO toDTO(Region region) {
        return new RegionDTO(region.getId(), region.getVshepData().getId(), region.getCode(), region.getNameRu(), region.getNameKz(), region.getNameEn());
    }
    public Region toEntity(RegionDTO dto, VshepData vshepData) {
        return new Region(dto.getId(), vshepData, dto.getCode(), dto.getNameRu(), dto.getNameKz(), dto.getNameEn());
    }
}