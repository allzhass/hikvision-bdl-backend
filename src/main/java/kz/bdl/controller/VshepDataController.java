package kz.bdl.controller;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.service.VshepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/vshep-data")
public class VshepDataController {
    @Autowired
    private VshepDataService vshepDataService;

    @GetMapping
    public List<VshepDataDTO> getAllVshepData() {
        return vshepDataService.getAllVshepData();
    }

    @GetMapping("/{id}")
    public VshepDataDTO getVshepDataById(@PathVariable Long id) {
        return vshepDataService.getVshepDataById(id);
    }

    @GetMapping("/{id}/data")
    public ResponseEntity<byte[]> getVshepData(@PathVariable Long id) {
        Optional<VshepDataDTO> vshepData = Optional.ofNullable(vshepDataService.getVshepDataById(id));
        if (vshepData.isPresent()) {
            byte[] data = vshepData.get().getCert();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cert.p12")
                    .body(data);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addVshepData(@RequestParam String clientId,
                                               @RequestParam String serviceId,
                                               @RequestParam String senderId,
                                               @RequestParam String senderPwd,
                                               @RequestParam String certpwd,
                                               @RequestParam(value = "url", required = false, defaultValue = "") String url,
                                               @RequestParam(value = "cert") MultipartFile cert) {
        try {
            return vshepDataService.addVshepData(VshepDataDTO.builder()
                    .clientId(clientId)
                    .serviceId(serviceId)
                    .senderId(senderId)
                    .senderPwd(senderPwd)
                    .cert(cert.getBytes())
                    .certpwd(certpwd)
                    .URL(url)
                    .build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeVshepData(@PathVariable Long id,
                                                  @RequestParam(value = "clientId", required = false) String clientId,
                                                  @RequestParam(value = "serviceId", required = false) String serviceId,
                                                  @RequestParam(value = "senderId", required = false) String senderId,
                                                  @RequestParam(value = "senderPwd", required = false) String senderPwd,
                                                  @RequestParam(value = "certpwd", required = false) String certpwd,
                                                  @RequestParam(value = "url", required = false) String url,
                                                  @RequestParam(value = "cert", required = false) MultipartFile cert) {
        VshepDataDTO vshepDataDTO = vshepDataService.getVshepDataById(id);
        if (vshepDataDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VshepData not found");
        }

        Optional.ofNullable(clientId).ifPresent(vshepDataDTO::setClientId);
        Optional.ofNullable(serviceId).ifPresent(vshepDataDTO::setServiceId);
        Optional.ofNullable(senderId).ifPresent(vshepDataDTO::setSenderId);
        Optional.ofNullable(senderPwd).ifPresent(vshepDataDTO::setSenderPwd);
        Optional.ofNullable(certpwd).ifPresent(vshepDataDTO::setCertpwd);
        Optional.ofNullable(url).ifPresent(vshepDataDTO::setURL);

        if (cert != null && !cert.isEmpty()) {
            try {
                vshepDataDTO.setCert(cert.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error reading certificate file: " + e.getMessage());
            }
        }

        return vshepDataService.changeVshepData(vshepDataDTO);
    }
}
