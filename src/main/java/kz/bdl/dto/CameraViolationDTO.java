package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraViolationDTO {
    private Long id;
    private Long cameraId;
    private Long violationId;
    private Boolean isSendErap;
}
