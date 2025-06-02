package kz.bdl.controller;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.service.VshepDataService;
import kz.bdl.service.CertificateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vshep-data-view")
public class VshepDataViewController {
    @Autowired
    private VshepDataService vshepDataService;
    
    @Autowired
    private CertificateDataService certificateDataService;

    @GetMapping
    public String getAllVshepData(Model model) {
        model.addAttribute("vshepDataList", vshepDataService.getAllVshepData());
        return "vshep-data/list";
    }

    @GetMapping("/{id}")
    public String getVshepDataById(@PathVariable Long id, Model model) {
        model.addAttribute("vshepData", vshepDataService.getVshepDataById(id));
        return "vshep-data/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("vshepData", new VshepDataDTO());
        model.addAttribute("certificates", certificateDataService.findAll());
        return "vshep-data/add";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addVshepData(@RequestParam String clientId,
                              @RequestParam String serviceId,
                              @RequestParam String senderId,
                              @RequestParam String senderPwd,
                              @RequestParam(value = "url", required = false, defaultValue = "") String url,
                              @RequestParam(value = "testUrl", required = false, defaultValue = "") String testUrl,
                              @RequestParam(value = "certId", required = false) Long certId) {
        vshepDataService.addVshepData(VshepDataDTO.builder()
                .clientId(clientId)
                .serviceId(serviceId)
                .senderId(senderId)
                .senderPwd(senderPwd)
                .URL(url)
                .testUrl(testUrl)
                .certId(certId)
                .build());
        return "redirect:/vshep-data-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("vshepData", vshepDataService.getVshepDataById(id));
        model.addAttribute("certificates", certificateDataService.findAll());
        return "vshep-data/edit";
    }

    @PostMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String changeVshepData(@PathVariable Long id,
                                 @RequestParam(value = "clientId", required = false) String clientId,
                                 @RequestParam(value = "serviceId", required = false) String serviceId,
                                 @RequestParam(value = "senderId", required = false) String senderId,
                                 @RequestParam(value = "senderPwd", required = false) String senderPwd,
                                 @RequestParam(value = "url", required = false) String url,
                                 @RequestParam(value = "testUrl", required = false) String testUrl,
                                 @RequestParam(value = "certId", required = false) Long certId) {
        VshepDataDTO vshepDataDTO = vshepDataService.getVshepDataById(id);
        if (vshepDataDTO == null) {
            return "redirect:/vshep-data-view";
        }

        if (clientId != null) vshepDataDTO.setClientId(clientId);
        if (serviceId != null) vshepDataDTO.setServiceId(serviceId);
        if (senderId != null) vshepDataDTO.setSenderId(senderId);
        if (senderPwd != null) vshepDataDTO.setSenderPwd(senderPwd);
        if (url != null) vshepDataDTO.setURL(url);
        if (testUrl != null) vshepDataDTO.setTestUrl(testUrl);
        if (certId != null) vshepDataDTO.setCertId(certId);

        vshepDataService.changeVshepData(vshepDataDTO);
        return "redirect:/vshep-data-view";
    }
} 