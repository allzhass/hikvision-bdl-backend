package kz.bdl.controller;

import kz.bdl.dto.SentViolationsDTO;
import kz.bdl.service.SentViolationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sent-violations-view")
@Slf4j
public class SentViolationsViewController {
    @Autowired
    private SentViolationsService sentViolationsService;

    @GetMapping
    public String getAllSentViolations(Model model) {
        log.info("getAllSentViolations start");
        List<SentViolationsDTO> violations = sentViolationsService.getAllSentViolations();
        model.addAttribute("violations", violations);
        model.addAttribute("title", "All Sent Violations");
        log.info("getAllSentViolations end");
        return "sent-violations/list";
    }

    @GetMapping("/camera/search")
    public String getSentViolationsByCameraId(
            @RequestParam(value = "name", required = false) String cameraName,
            @RequestParam(value = "ip", required = false) String cameraIp,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        log.info("getSentViolationsByCameraId start: cameraName={}, cameraIp={}, page={}, size={}", cameraName, cameraIp, page, size);

        if (cameraIp != null && !cameraIp.isEmpty()) {
            Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolationsByCameraIp(cameraIp, page, size);
            model.addAttribute("violations", violationsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", violationsPage.getTotalPages());
            model.addAttribute("title", "Sent Violations by Camera IP");
            model.addAttribute("cameraIp", cameraIp);
        } else if (cameraName != null && !cameraName.isEmpty()) {
            Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolationsByCameraName(cameraName, page, size);
            model.addAttribute("violations", violationsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", violationsPage.getTotalPages());
            model.addAttribute("title", "Sent Violations by Camera Name");
            model.addAttribute("cameraName", cameraName);
        } else {
            log.info("getSentViolationsByCameraId end - redirecting to paginated view");
            return "redirect:/sent-violations-view/paginated";
        }
        log.info("getSentViolationsByCameraId end");
        return "sent-violations/paginated-list";
    }

    @GetMapping("/paginated")
    public String getPaginatedList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        log.info("getPaginatedList start: page={}, size={}", page, size);
        Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolations(page, size);
        
        model.addAttribute("violations", violationsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", violationsPage.getTotalPages());
        model.addAttribute("title", "Sent Violations (Paginated)");
        
        log.info("getPaginatedList end");
        return "sent-violations/paginated-list";
    }

    @GetMapping("/plate/search")
    public String getSentViolationsByPlateNumber(
            @RequestParam(value = "plateNumber", required = false) String plateNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        log.info("getSentViolationsByPlateNumber start: plateNumber={}, page={}, size={}", plateNumber, page, size);

        if (plateNumber != null && !plateNumber.isEmpty()) {
            Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolationsByPlateNumber(plateNumber, page, size);
            model.addAttribute("violations", violationsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", violationsPage.getTotalPages());
            model.addAttribute("title", "Sent Violations by Plate Number");
            model.addAttribute("plateNumber", plateNumber);
        } else {
            log.info("getSentViolationsByPlateNumber end - redirecting to paginated view");
            return "redirect:/sent-violations-view/paginated";
        }
        log.info("getSentViolationsByPlateNumber end");
        return "sent-violations/paginated-list";
    }

    @GetMapping("/region/search")
    public String getSentViolationsByRegion(
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        log.info("getSentViolationsByRegion start: region={}, page={}, size={}", region, page, size);

        if (region != null && !region.isEmpty()) {
            Page<SentViolationsDTO> violationsPage = sentViolationsService.getPaginatedSentViolationsByRegionCode(region, page, size);
            model.addAttribute("violations", violationsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", violationsPage.getTotalPages());
            model.addAttribute("title", "Sent Violations by Region Code");
            model.addAttribute("region", region);
        } else {
            log.info("getSentViolationsByRegion end - redirecting to paginated view");
            return "redirect:/sent-violations-view/paginated";
        }
        log.info("getSentViolationsByRegion end");
        return "sent-violations/paginated-list";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SentViolationsDTO getSentViolationById(@PathVariable Long id) {
        log.info("getSentViolationById start: {}", id);
        SentViolationsDTO result = sentViolationsService.getSentViolationById(id);
        log.info("getSentViolationById end");
        return result;
    }
}