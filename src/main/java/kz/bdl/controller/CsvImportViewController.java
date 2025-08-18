package kz.bdl.controller;

import kz.bdl.service.DataImportService;
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
@RequestMapping("/data-import-view")
@Slf4j
public class CsvImportViewController {

    @Autowired private DataImportService dataImportService;

    @GetMapping
    public String showImportForm(Model model) {
        log.info("showImportForm start");
        return "data-import/import";
    }

    @PostMapping("/upload/xlsx")
    public String uploadXlsxFile(@RequestParam("file") MultipartFile file, 
                               RedirectAttributes redirectAttributes) {
        log.info("uploadCsvFile start: {}", file.getOriginalFilename());
        
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload");
            return "redirect:/data-import-view";
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
            redirectAttributes.addFlashAttribute("error", "Please upload a XLSX file");
            return "redirect:/data-import-view";
        }

        try {
            String result = dataImportService.importXlsxData(file);
            redirectAttributes.addFlashAttribute("success", result);
            log.info("uploadXlsxFile end: success");
        } catch (Exception e) {
            String errorMessage = "Error importing XLSX file: " + e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            log.error("uploadXlsxFile end: error - {}", errorMessage);
        }

        return "redirect:/data-import-view";
    }
}
