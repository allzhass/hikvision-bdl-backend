package kz.bdl.converter;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.entity.VshepData;
import org.springframework.stereotype.Component;

@Component
public class VshepDataConverter {
    
    public VshepDataDTO toDTO(VshepData entity) {
        if (entity == null) {
            return null;
        }
        
        return VshepDataDTO.builder()
                .id(entity.getId())
                .certId(entity.getCertId() != null ? entity.getCertId().getId() : null)
                .certIssuedBy(entity.getCertId() != null ? entity.getCertId().getIssuedBy() : null)
                .clientId(entity.getClientId())
                .serviceId(entity.getServiceId())
                .senderId(entity.getSenderId())
                .senderPwd(entity.getSenderPwd())
                .URL(entity.getURL())
                .testUrl(entity.getTestUrl())
                .build();
    }
    
    public VshepData toEntity(VshepDataDTO dto) {
        if (dto == null) {
            return null;
        }
        
        VshepData entity = new VshepData();
        entity.setId(dto.getId());
        entity.setClientId(dto.getClientId());
        entity.setServiceId(dto.getServiceId());
        entity.setSenderId(dto.getSenderId());
        entity.setSenderPwd(dto.getSenderPwd());
        entity.setURL(dto.getURL());
        entity.setTestUrl(dto.getTestUrl());
        
        return entity;
    }
}
