package kz.bdl.controller;

import kz.bdl.dto.VshepDataDTO;
import kz.bdl.service.VshepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/vshep-data-view")
public class VshepDataViewController {
    @Autowired
    private VshepDataService vshepDataService;

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

    @GetMapping("/{id}/download")
    public String downloadVshepData(@PathVariable Long id) {
        return "redirect:/vshep-data/" + id + "/data";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("vshepData", new VshepDataDTO());
        return "vshep-data/add";
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addVshepData(@RequestParam String clientId,
                              @RequestParam String serviceId,
                              @RequestParam String senderId,
                              @RequestParam String senderPwd,
                              @RequestParam String certpwd,
                              @RequestParam(value = "url", required = false, defaultValue = "") String url,
                              @RequestParam(value = "cert") MultipartFile cert) {
        try {
            vshepDataService.addVshepData(VshepDataDTO.builder()
                    .clientId(clientId)
                    .serviceId(serviceId)
                    .senderId(senderId)
                    .senderPwd(senderPwd)
                    .cert(cert.getBytes())
                    .certpwd(certpwd)
                    .URL(url)
                    .build());
            return "redirect:/vshep-data-view";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("vshepData", vshepDataService.getVshepDataById(id));
        return "vshep-data/edit";
    }

    @PostMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String changeVshepData(@PathVariable Long id,
                                 @RequestParam(value = "clientId", required = false) String clientId,
                                 @RequestParam(value = "serviceId", required = false) String serviceId,
                                 @RequestParam(value = "senderId", required = false) String senderId,
                                 @RequestParam(value = "senderPwd", required = false) String senderPwd,
                                 @RequestParam(value = "certpwd", required = false) String certpwd,
                                 @RequestParam(value = "url", required = false) String url,
                                 @RequestParam(value = "cert", required = false) MultipartFile cert) {
        VshepDataDTO vshepDataDTO = vshepDataService.getVshepDataById(id);
        if (vshepDataDTO == null) {
            return "redirect:/vshep-data-view";
        }

        if (clientId != null) vshepDataDTO.setClientId(clientId);
        if (serviceId != null) vshepDataDTO.setServiceId(serviceId);
        if (senderId != null) vshepDataDTO.setSenderId(senderId);
        if (senderPwd != null) vshepDataDTO.setSenderPwd(senderPwd);
        if (certpwd != null) vshepDataDTO.setCertpwd(certpwd);
        if (url != null) vshepDataDTO.setURL(url);

        if (cert != null && !cert.isEmpty()) {
            try {
                vshepDataDTO.setCert(cert.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        vshepDataService.changeVshepData(vshepDataDTO);
        return "redirect:/vshep-data-view";
    }
} 