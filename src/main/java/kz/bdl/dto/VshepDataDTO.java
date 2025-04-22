package kz.bdl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VshepDataDTO {
    private Long id;
    private String clientId;
    private String serviceId;
    private String senderId;
    private String senderPwd;
    private byte[] cert;
    private String certpwd;
    private String URL;
}
