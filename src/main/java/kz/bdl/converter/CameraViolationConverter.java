package kz.bdl.converter;

import kz.bdl.dto.CameraViolationDTO;
import kz.bdl.entity.Camera;
import kz.bdl.entity.CameraViolation;
import kz.bdl.entity.Violation;
import org.springframework.stereotype.Component;

@Component
public class CameraViolationConverter {
    public CameraViolationDTO toDTO(CameraViolation cameraViolation) {
        return new CameraViolationDTO(
                cameraViolation.getId(),
                cameraViolation.getCamera(),
                cameraViolation.getViolation(),
                cameraViolation.getIsSendErap(),
                cameraViolation.getIsProd());
    }
    public CameraViolation toEntity(CameraViolationDTO dto, Camera camera, Violation violation) {
        return new CameraViolation(
                dto.getId(),
                camera,
                violation,
                dto.getIsSendErap(),
                dto.getIsProd());
    }
}