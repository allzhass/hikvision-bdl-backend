package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentViolationsDTO {
    private Long id;
    private Long cameraId;
    private String cameraName;
    private Long apkId;
    private String apkName;
    private Long violationId;
    private String violationCode;
    private String request;
    private String response;
    private LocalDateTime createdAt;
} 