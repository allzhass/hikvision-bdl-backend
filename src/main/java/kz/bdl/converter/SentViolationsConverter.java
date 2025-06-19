package kz.bdl.converter;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.entity.SentViolations;
import org.springframework.stereotype.Component;

@Component
public class SentViolationsConverter {
    public SentViolationsDTO toDTO(SentViolations sentViolations) {
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
            sentViolations.getCreatedAt(),
            sentViolations.getPlateNumber(),
            sentViolations.getMessageId()
        );
    }
} 