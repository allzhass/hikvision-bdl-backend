package kz.bdl.converter;

import kz.bdl.dto.CameraDTO;
import kz.bdl.entity.APK;
import kz.bdl.entity.Camera;
import org.springframework.stereotype.Component;

@Component
public class CameraConverter {
    public CameraDTO toDTO(Camera camera) {
        return new CameraDTO(camera.getId(), camera.getApk().getId(), camera.getName(), camera.getIp(), camera.getCode(), camera.getDirection(), camera.getBrand(), camera.getCollectionDays());
    }
    public Camera toEntity(CameraDTO dto, APK apk) {
        return new Camera(dto.getId(), apk, dto.getName(), dto.getIp(), dto.getCode(), dto.getDirection(), dto.getBrand(), dto.getCollectionDays());
    }
}