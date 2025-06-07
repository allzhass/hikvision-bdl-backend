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
                .name(entity.getName())
                .certId(entity.getCertId() != null ? entity.getCertId().getId() : null)
                .certIssuedBy(entity.getCertId() != null ? entity.getCertId().getIssuedBy() : null)
                .clientId(entity.getClientId())
                .serviceId(entity.getServiceId())
                .senderId(entity.getSenderId())
                .senderPwd(entity.getSenderPwd())
                .source(entity.getSource())
                .URL(entity.getURL())
                .testUrl(entity.getTestUrl())
                .build();
    }

    public VshepDataDTO toDTOShort(VshepData entity) {
        if (entity == null) {
            return null;
        }

        return VshepDataDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .clientId(entity.getClientId())
                .serviceId(entity.getServiceId())
                .senderId(entity.getSenderId())
                .senderPwd(entity.getSenderPwd())
                .source(entity.getSource())
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
        entity.setName(dto.getName());
        entity.setClientId(dto.getClientId());
        entity.setServiceId(dto.getServiceId());
        entity.setSenderId(dto.getSenderId());
        entity.setSenderPwd(dto.getSenderPwd());
        entity.setURL(dto.getURL());
        entity.setTestUrl(dto.getTestUrl());
        entity.setSource(dto.getSource());
        
        return entity;
    }
}
