package kz.bdl.controller;

import kz.bdl.dto.AutoDto;
import kz.bdl.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autos-view")
public class AutoViewController {

    private final AutoService autoService;

    @Autowired
    public AutoViewController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping
    public String listAutos(Model model) {
        model.addAttribute("autos", autoService.getAllAutos());
        model.addAttribute("newAuto", new AutoDto());
        return "auto/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("auto", new AutoDto());
        return "auto/create";
    }

    @PostMapping
    public String createAuto(@ModelAttribute AutoDto autoDto, RedirectAttributes redirectAttributes) {
        try {
            autoService.createAuto(autoDto);
            redirectAttributes.addFlashAttribute("successMessage", "Автомобиль успешно добавлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении автомобиля: " + e.getMessage());
        }
        return "redirect:/autos-view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AutoDto autoDto = autoService.getAutoById(id);
        model.addAttribute("auto", autoDto);
        return "auto/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAuto(@PathVariable Long id, @ModelAttribute AutoDto autoDto, RedirectAttributes redirectAttributes) {
        try {
            autoService.updateAuto(id, autoDto);
            redirectAttributes.addFlashAttribute("successMessage", "Автомобиль успешно обновлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении автомобиля: " + e.getMessage());
        }
        return "redirect:/autos-view";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            autoService.deleteAuto(id);
            redirectAttributes.addFlashAttribute("successMessage", "Автомобиль успешно удален!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении автомобиля: " + e.getMessage());
        }
        return "redirect:/autos-view";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пожалуйста, выберите файл для загрузки");
            return "redirect:/autos-view";
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пожалуйста, загрузите файл в формате CSV");
            return "redirect:/autos-view";
        }

        try {
            List<AutoDto> autos = new ArrayList<>();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
            );
            
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header row
                }

                String[] data = line.split(",");
                if (data.length >= 3) {
                    AutoDto autoDto = new AutoDto();
                    autoDto.setPlateNumber(data[0].trim());
                    autoDto.setDescription(data[1].trim());
                    autoDto.setIsSendViolation(Boolean.parseBoolean(data[2].trim()));
                    autos.add(autoDto);
                }
            }

            int successCount = 0;
            for (AutoDto autoDto : autos) {
                try {
                    autoService.createAuto(autoDto);
                    successCount++;
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("errorMessage", 
                        String.format("Ошибка при сохранении автомобиля %s: %s", 
                            autoDto.getPlateNumber(), e.getMessage()));
                    return "redirect:/autos-view";
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", 
                String.format("Успешно загружено %d автомобилей из файла", successCount));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Ошибка при обработке файла: " + e.getMessage());
        }

        return "redirect:/autos-view";
    }
} 