package kz.bdl.converter;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.entity.SentViolations;
import org.springframework.stereotype.Component;

@Component
public class SentViolationsConverter {
    public SentViolationsDTO toDTO(SentViolations sentViolations) {
        return new SentViolationsDTO(
            sentViolations.getId(),
            sentViolations.getCameraViolation().getCamera().getId(),
            sentViolations.getCameraViolation().getCamera().getName(),
            sentViolations.getCameraViolation().getCamera().getApk().getId(),
            sentViolations.getCameraViolation().getCamera().getApk().getDeviceNumber(),
            sentViolations.getCameraViolation().getViolation().getId(),
            sentViolations.getCameraViolation().getViolation().getCode(),
            sentViolations.getRequest(),
            sentViolations.getResponse(),
            sentViolations.getCreatedAt()
        );
    }
} 