package kz.bdl.converter;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.entity.SentViolations;
import kz.bdl.util.TimezoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SentViolationsConverter {
    
    @Autowired
    private TimezoneUtil timezoneUtil;
    
    public SentViolationsDTO toDTO(SentViolations sentViolations) {
        // Convert GMT time to local time using utility
        LocalDateTime localCreatedAt = timezoneUtil.convertGmtToLocal(sentViolations.getCreatedAt());
        
        return new SentViolationsDTO(
            sentViolations.getId(),
                sentViolations.getCameraViolation() != null ? sentViolations.getCameraViolation().getCamera().getId() : null,
                sentViolations.getCameraViolation() != null ? sentViolations.getCameraViolation().getCamera().getName() : null,
                sentViolations.getCameraViolation() != null ? sentViolations.getCameraViolation().getCamera().getApk().getId() : null,
                sentViolations.getCameraViolation() != null ? sentViolations.getCameraViolation().getCamera().getApk().getDeviceNumber() : null,
                sentViolations.getCameraViolation() != null ? sentViolations.getCameraViolation().getViolation().getId() : null,
                sentViolations.getCameraViolation() != null ? sentViolations.getCameraViolation().getViolation().getCode() : null,
            sentViolations.getRequest(),
            sentViolations.getResponse(),
            sentViolations.getIsError(),
            localCreatedAt,
            sentViolations.getPlateNumber(),
            sentViolations.getMessageId()
        );
    }
} 