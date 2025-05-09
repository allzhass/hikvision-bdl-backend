package kz.bdl.dto;

import kz.bdl.entity.Camera;
import kz.bdl.entity.Violation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraViolationDTO {
    private Long id;
    private Camera camera;
    private Violation violation;
    private Boolean isSendErap;
}
