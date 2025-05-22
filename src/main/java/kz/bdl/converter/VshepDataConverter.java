package kz.bdl.converter;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.entity.VshepData;
import org.springframework.stereotype.Component;

@Component
public class VshepDataConverter {
    public VshepDataDTO toDTO(VshepData vshepData) {
        return new VshepDataDTO(
                vshepData.getId(),
                vshepData.getClientId(),
                vshepData.getServiceId(),
                vshepData.getSenderId(),
                vshepData.getSenderPwd(),
                vshepData.getCert(),
                vshepData.getCertpwd(),
                vshepData.getURL(),
                vshepData.getTestUrl());
    }
    public VshepData toEntity(VshepDataDTO dto) {
        return new VshepData(
                dto.getId(),
                dto.getClientId(),
                dto.getServiceId(),
                dto.getSenderId(),
                dto.getSenderPwd(),
                dto.getCert(),
                dto.getCertpwd(),
                dto.getURL(),
                dto.getTestUrl());
    }
}
