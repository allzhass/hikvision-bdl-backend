package kz.bdl.converter;

import kz.bdl.dto.APKDTO;
import kz.bdl.entity.APK;
import kz.bdl.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class APKConverter {
    public APKDTO toDTO(APK apk) {
        return new APKDTO(apk.getId(), apk.getLocation().getId(), apk.getDeviceNumber(), apk.getCertNumber(), apk.getCertIssue(), apk.getCertExpiry());
    }
    public APK toEntity(APKDTO dto, Location location) {
        return new APK(dto.getId(), location, dto.getDeviceNumber(), dto.getCertNumber(), dto.getCertIssue(), dto.getCertExpiry());
    }
}