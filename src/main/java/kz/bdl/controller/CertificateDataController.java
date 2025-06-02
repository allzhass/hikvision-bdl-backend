package kz.bdl.controller;

import kz.bdl.entity.CertificateData;
import kz.bdl.service.CertificateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/certificates")
public class CertificateDataController {

    private final CertificateDataService certificateDataService;

    @Autowired
    public CertificateDataController(CertificateDataService certificateDataService) {
        this.certificateDataService = certificateDataService;
    }

    @GetMapping
    public String listCertificates(Model model) {
        model.addAttribute("certificates", certificateDataService.findAll());
        return "certificates/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("certificate", new CertificateData());
        return "certificates/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        certificateDataService.findById(id).ifPresent(certificate -> model.addAttribute("certificate", certificate));
        return "certificates/form";
    }

    @PostMapping("/save")
    public String saveCertificate(@ModelAttribute CertificateData certificate,
                                @RequestParam(value = "certFile", required = false) MultipartFile certFile) {
        
        CertificateData certificateData = certificateDataService.findById(certificate.getId()).orElseThrow(() -> new RuntimeException("Certificate not found"));
        if (certFile != null && !certFile.isEmpty()) {
            try {
                byte[] certBytes = certFile.getBytes();
                String base64Cert = Base64.getEncoder().encodeToString(certBytes);
                certificate.setCert(base64Cert);
            } catch (IOException e) {
                throw new RuntimeException("Failed to process certificate file", e);
            }
        } else {
            certificate.setCert(certificateData.getCert());
        }
        
        certificateDataService.save(certificate);
        return "redirect:/certificates";
    }

    @GetMapping("/delete/{id}")
    public String deleteCertificate(@PathVariable Long id) {
        certificateDataService.deleteById(id);
        return "redirect:/certificates";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) throws IOException {
        Optional<CertificateData> certificate = certificateDataService.findById(id);
        if (certificate.isPresent() && certificate.get().getCert() != null) {
            byte[] certBytes = Base64.getDecoder().decode(certificate.get().getCert());
            if (certBytes != null) {
            return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cert.p12")
                            .body(certBytes);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
} 