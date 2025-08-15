package kz.bdl.controller;

import kz.bdl.service.CsvImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/csv-import-view")
@Slf4j
public class CsvImportViewController {

    @Autowired private CsvImportService csvImportService;

    @GetMapping
    public String showImportForm(Model model) {
        log.info("showImportForm start");
        return "csv-import/import";
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file, 
                               RedirectAttributes redirectAttributes) {
        log.info("uploadCsvFile start: {}", file.getOriginalFilename());
        
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload");
            return "redirect:/csv-import-view";
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            redirectAttributes.addFlashAttribute("error", "Please upload a CSV file");
            return "redirect:/csv-import-view";
        }

        try {
            String result = csvImportService.importCsvData(file);
            redirectAttributes.addFlashAttribute("success", result);
            log.info("uploadCsvFile end: success");
        } catch (Exception e) {
            String errorMessage = "Error importing CSV file: " + e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            log.error("uploadCsvFile end: error - {}", errorMessage);
        }

        return "redirect:/csv-import-view";
    }
}
