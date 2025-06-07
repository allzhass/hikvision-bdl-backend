package kz.bdl.controller;

import kz.bdl.entity.CertificateData;
import kz.bdl.service.CertificateDataService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CertificateDataController {

    private final CertificateDataService certificateDataService;

    @Autowired
    public CertificateDataController(CertificateDataService certificateDataService) {
        this.certificateDataService = certificateDataService;
    }

    @GetMapping
    public String listCertificates(Model model) {
        log.info("listCertificates start");
        model.addAttribute("certificates", certificateDataService.findAll());
        log.info("listCertificates end");
        return "certificates/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        log.info("showCreateForm start");
        model.addAttribute("certificate", new CertificateData());
        log.info("showCreateForm end");
        return "certificates/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("showEditForm start: {}", id);
        certificateDataService.findById(id).ifPresent(certificate -> model.addAttribute("certificate", certificate));
        log.info("showEditForm end");
        return "certificates/form";
    }

    @PostMapping("/save")
    public String saveCertificate(@RequestParam(required = false) Long id,
                                @RequestParam String issuedBy,
                                @RequestParam String issuedAt,
                                @RequestParam String expiresAt,
                                @RequestParam(required = false) String certpwd,
                                @RequestParam(value = "certFile", required = false) MultipartFile certFile) {
        log.info("saveCertificate start: id={}, issuedBy={}, issuedAt={}, expiresAt={}", id, issuedBy, issuedAt, expiresAt);
        CertificateData certificate = new CertificateData();
        certificate.setId(id);
        certificate.setIssuedBy(issuedBy);
        certificate.setIssuedAt(issuedAt);
        certificate.setExpiresAt(expiresAt);
        
        if (certFile != null && !certFile.isEmpty()) {
            try {
                byte[] certBytes = certFile.getBytes();
                String base64Cert = Base64.getEncoder().encodeToString(certBytes);
                certificate.setCert(base64Cert);
            } catch (IOException e) {
                log.error("Failed to process certificate file", e);
                throw new RuntimeException("Failed to process certificate file", e);
            }
        } else if (id != null) {
            CertificateData existingCertificate = certificateDataService.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
            certificate.setCert(existingCertificate.getCert());
        }

        System.out.println("CERTPWD: " + certpwd);
        if (certpwd != null && !certpwd.isEmpty()) {
            certificate.setCertpwd(certpwd);
        } else if (id != null) {
            CertificateData existingCertificate = certificateDataService.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
            certificate.setCertpwd(existingCertificate.getCertpwd());
        }
        
        certificateDataService.save(certificate);
        log.info("saveCertificate end");
        return "redirect:/certificates";
    }

    @GetMapping("/delete/{id}")
    public String deleteCertificate(@PathVariable Long id) {
        log.info("deleteCertificate start: {}", id);
        certificateDataService.deleteById(id);
        log.info("deleteCertificate end");
        return "redirect:/certificates";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) throws IOException {
        log.info("downloadCertificate start: {}", id);
        Optional<CertificateData> certificate = certificateDataService.findById(id);
        if (certificate.isPresent() && certificate.get().getCert() != null) {
            byte[] certBytes = Base64.getDecoder().decode(certificate.get().getCert());
            if (certBytes != null) {
                log.info("downloadCertificate end");
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cert.p12")
                    .body(certBytes);
            }
        }
        log.info("downloadCertificate end - not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
} 