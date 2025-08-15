package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvImportDTO {
    private String regionCode;
    private String locationNameRu;
    private String apkDeviceNumber;
    private String apkCertNumber;
    private LocalDateTime apkCertFrom;
    private LocalDateTime apkCertTo;
    private String cameraIp;
    private String cameraCode;
    private String cameraDirection;
}
