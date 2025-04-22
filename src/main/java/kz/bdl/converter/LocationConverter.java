package kz.bdl.converter;

import kz.bdl.dto.LocationDTO;
import kz.bdl.entity.Location;
import kz.bdl.entity.Region;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {
    public LocationDTO toDTO(Location location) {
        return new LocationDTO(location.getId(), location.getRegion().getId(), location.getNameRu(), location.getNameKz(), location.getNameEn());
    }
    public Location toEntity(LocationDTO dto, Region region) {
        return new Location(dto.getId(), region, dto.getNameRu(), dto.getNameKz(), dto.getNameEn());
    }
}