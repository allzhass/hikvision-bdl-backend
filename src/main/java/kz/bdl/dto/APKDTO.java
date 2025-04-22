package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APKDTO {
    private Long id;
    private Long locationId;
    private String deviceNumber;
    private String certNumber;
    private LocalDateTime certIssue;
    private LocalDateTime certExpiry;
}