package kz.bdl.controller;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.service.VshepDataService;
import kz.bdl.service.CertificateDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vshep-data-view")
@Slf4j
public class VshepDataViewController {
    @Autowired
    private VshepDataService vshepDataService;
    
    @Autowired
    private CertificateDataService certificateDataService;

    @GetMapping
    public String getAllVshepData(Model model) {
        log.info("getAllVshepData start");
        model.addAttribute("vshepDataList", vshepDataService.getAllVshepData());
        log.info("getAllVshepData end");
        return "vshep-data/list";
    }

    @GetMapping("/{id}")
    public String getVshepDataById(@PathVariable Long id, Model model) {
        log.info("getVshepDataById start: {}", id);
        model.addAttribute("vshepData", vshepDataService.getVshepDataById(id));
        log.info("getVshepDataById end");
        return "vshep-data/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("showAddForm start");
        model.addAttribute("vshepData", new VshepDataDTO());
        model.addAttribute("certificates", certificateDataService.findAll());
        log.info("showAddForm end");
        return "vshep-data/add";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addVshepData(@RequestParam String name,
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
        vshepDataService.addVshepData(VshepDataDTO.builder()
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
        return "redirect:/vshep-data-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        model.addAttribute("vshepData", vshepDataService.getVshepDataById(id));
        model.addAttribute("certificates", certificateDataService.findAll());
        log.info("showEditForm end");
        return "vshep-data/edit";
    }

    @PostMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String changeVshepData(@PathVariable Long id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "clientId", required = false) String clientId,
                                 @RequestParam(value = "serviceId", required = false) String serviceId,
                                 @RequestParam(value = "senderId", required = false) String senderId,
                                 @RequestParam(value = "senderPwd", required = false) String senderPwd,
                                 @RequestParam(value = "source", required = false) String source,
                                 @RequestParam(value = "url", required = false) String url,
                                 @RequestParam(value = "testUrl", required = false) String testUrl,
                                 @RequestParam(value = "certId", required = false) Long certId) {
        log.info("changeVshepData start: id={}, name={}, clientId={}, serviceId={}, senderId={}, source={}, url={}, testUrl={}, certId={}",
                id, name, clientId, serviceId, senderId, source, url, testUrl, certId);
        VshepDataDTO vshepDataDTO = vshepDataService.getVshepDataById(id);
        if (vshepDataDTO == null) {
            return "redirect:/vshep-data-view";
        }

        if (name != null) vshepDataDTO.setName(name);
        if (clientId != null) vshepDataDTO.setClientId(clientId);
        if (serviceId != null) vshepDataDTO.setServiceId(serviceId);
        if (senderId != null) vshepDataDTO.setSenderId(senderId);
        if (senderPwd != null) vshepDataDTO.setSenderPwd(senderPwd);
        if (source != null) vshepDataDTO.setSource(source);
        if (url != null) vshepDataDTO.setURL(url);
        if (testUrl != null) vshepDataDTO.setTestUrl(testUrl);
        if (certId != null) vshepDataDTO.setCertId(certId);

        vshepDataService.changeVshepData(vshepDataDTO);
        log.info("changeVshepData end");
        return "redirect:/vshep-data-view";
    }
} 