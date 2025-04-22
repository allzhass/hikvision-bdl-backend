package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraDTO {
    private Long id;
    private Long apkId;
    private String name;
    private String ip;
    private String code;
    private String direction;
    private String brand;
    private Integer collectionDays;
}