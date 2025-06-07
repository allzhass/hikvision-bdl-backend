package kz.bdl.controller;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.service.VshepDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vshep-data")
@Slf4j
public class VshepDataController {
    @Autowired
    private VshepDataService vshepDataService;

    @GetMapping
    public List<VshepDataDTO> getAllVshepData() {
        log.info("getAllVshepData start");
        List<VshepDataDTO> result = vshepDataService.getAllVshepData();
        log.info("getAllVshepData end");
        return result;
    }

    @GetMapping("/{id}")
    public VshepDataDTO getVshepDataById(@PathVariable Long id) {
        log.info("getVshepDataById start: {}", id);
        VshepDataDTO result = vshepDataService.getVshepDataById(id);
        log.info("getVshepDataById end");
        return result;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addVshepData(@RequestParam String name,
                                               @RequestParam String clientId,
                                               @RequestParam String serviceId,
                                               @RequestParam String senderId,
                                               @RequestParam String senderPwd,
                                               @RequestParam String source,
                                               @RequestParam(value = "url", required = false, defaultValue = "") String url,
                                               @RequestParam(value = "testUrl", required = false, defaultValue = "") String testUrl,
                                               @RequestParam(value = "certId", required = false) Long certId) {
        log.info("addVshepData start: name={}, clientId={}, serviceId={}, senderId={}, source={}, url={}, testUrl={}, certId={}",
                name, clientId, serviceId, senderId, source, url, testUrl, certId);
        ResponseEntity<String> result = vshepDataService.addVshepData(VshepDataDTO.builder()
                .name(name)
                .clientId(clientId)
                .serviceId(serviceId)
                .senderId(senderId)
                .senderPwd(senderPwd)
                .source(source)
                .URL(url)
                .testUrl(testUrl)
                .certId(certId)
                .build());
        log.info("addVshepData end");
        return result;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeVshepData(@PathVariable Long id,
                                                  @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "clientId", required = false) String clientId,
                                                  @RequestParam(value = "serviceId", required = false) String serviceId,
                                                  @RequestParam(value = "senderId", required = false) String senderId,
                                                  @RequestParam(value = "senderPwd", required = false) String senderPwd,
                                                  @RequestParam(value = "source", required = false) String source,
                                                  @RequestParam(value = "url", required = false) String url,
                                                  @RequestParam(value = "testUrl", required = false) String testUrl,
                                                  @RequestParam(value = "certId", required = false) Long certId) {
        VshepDataDTO vshepDataDTO = vshepDataService.getVshepDataById(id);
        if (vshepDataDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VshepData not found");
        }

        if (name == null) vshepDataDTO.setName(name);
        if (clientId != null) vshepDataDTO.setClientId(clientId);
        if (serviceId != null) vshepDataDTO.setServiceId(serviceId);
        if (senderId != null) vshepDataDTO.setSenderId(senderId);
        if (senderPwd != null) vshepDataDTO.setSenderPwd(senderPwd);
        if (source != null) vshepDataDTO.setSource(source);
        if (url != null) vshepDataDTO.setURL(url);
        if (testUrl != null) vshepDataDTO.setTestUrl(testUrl);
        if (certId != null) vshepDataDTO.setCertId(certId);

        return vshepDataService.changeVshepData(vshepDataDTO);
    }
}
