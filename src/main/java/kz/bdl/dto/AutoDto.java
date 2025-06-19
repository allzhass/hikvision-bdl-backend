package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoDto {
    private Long id;
    private String plateNumber;
    private String description;
    private Boolean isSendViolation;
} 