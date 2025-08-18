package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDataImportDTO {
    private String regionCode;
    private String locationNameRu;
    private String apkDeviceNumber;
    private String apkCertNumber;
    private LocalDateTime apkCertFrom;
    private LocalDateTime apkCertTo;
    private String cameraIp;
    private String cameraCode;
    private String cameraDirection;

    public FileDataImportDTO(String regionCode) {
        this.regionCode = regionCode;
    }
}
