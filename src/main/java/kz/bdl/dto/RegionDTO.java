package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
    private Long id;
    private Long vshepDataId;
    private String code;
    private String nameRu;
    private String nameKz;
    private String nameEn;
}
