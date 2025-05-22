package kz.bdl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vshep_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VshepData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String serviceId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String senderPwd;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] cert;

    @Column(nullable = false)
    private String certpwd;

    @Column
    private String URL;

    @Column
    private String testUrl;
}
