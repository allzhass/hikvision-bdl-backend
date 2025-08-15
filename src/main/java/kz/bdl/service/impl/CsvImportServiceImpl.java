package kz.bdl.service.impl;

import kz.bdl.dto.CsvImportDTO;
import kz.bdl.entity.APK;
import kz.bdl.entity.Camera;
import kz.bdl.entity.Location;
import kz.bdl.entity.Region;
import kz.bdl.exception.ResourceNotFoundException;
import kz.bdl.repository.APKRepository;
import kz.bdl.repository.CameraRepository;
import kz.bdl.repository.LocationRepository;
import kz.bdl.repository.RegionRepository;
import kz.bdl.service.CsvImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Propagation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvImportServiceImpl implements CsvImportService {

    @Autowired private RegionRepository regionRepository;
    @Autowired private LocationRepository locationRepository;
    @Autowired private APKRepository apkRepository;
    @Autowired private CameraRepository cameraRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter MM_DD_YY_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

    @Override
    public String importCsvData(MultipartFile file) {
        log.info("Starting CSV import for file: {}", file.getOriginalFilename());
        
        List<String> errors = new ArrayList<>();
        int processedRows = 0;
        int successRows = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                processedRows++;
                
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                try {
                    processCsvRowWithTransaction(line);
                    successRows++;
                } catch (Exception e) {
                    String error = String.format("Row %d: %s", processedRows, e.getMessage());
                    errors.add(error);
                    log.error("Error processing row {}: {}", processedRows, e.getMessage());
                    // Don't throw here - continue processing other rows
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }

        String result = String.format("Import completed. Processed: %d, Success: %d, Errors: %d", 
                processedRows - 1, successRows, errors.size());
        
        if (!errors.isEmpty()) {
            result += "\nErrors:\n" + String.join("\n", errors);
        }
        
        log.info("CSV import completed: {}", result);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    private void processCsvRowWithTransaction(String line) {
        processCsvRow(line);
    }

    private void processCsvRow(String line) {
        String[] values = line.split(";");
        if (values.length < 9) {
            log.error("Invalid CSV format: length: {}, line: {}", values.length, line);
            throw new IllegalArgumentException("Invalid CSV format: length: " + values.length + ", line: " + line);
        }

        CsvImportDTO csvData = new CsvImportDTO();
        csvData.setRegionCode(values[0].trim());
        csvData.setLocationNameRu(values[1].trim());
        csvData.setApkDeviceNumber(values[2].trim());
        csvData.setApkCertNumber(values[3].trim());
        csvData.setApkCertFrom(parseDateTime(values[4].trim()));
        csvData.setApkCertTo(parseDateTime(values[5].trim()));
        csvData.setCameraIp(values[6].trim());
        csvData.setCameraCode(values[7].trim());
        csvData.setCameraDirection(values[8].trim());

        processCsvData(csvData);
    }

    private LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        
        String trimmedDate = dateStr.trim();
        
        // Try to parse as mm/dd/yy format first
        try {
            if (trimmedDate.matches("\\d{1,2}/\\d{1,2}/\\d{2}")) {
                // Convert mm/dd/yy to LocalDate, then to LocalDateTime at start of day
                return java.time.LocalDate.parse(trimmedDate, MM_DD_YY_FORMATTER)
                        .atStartOfDay();
            }
        } catch (Exception e) {
            // Continue to try the original format
        }
        
        try {
            return LocalDateTime.parse(trimmedDate, DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr + 
                    ". Expected formats: MM/dd/yy or yyyy-MM-dd HH:mm:ss");
        }
    }

    private void processCsvData(CsvImportDTO csvData) {
        log.info("Processing CSV data: {}", csvData);
        
        // Validate required fields
        if (csvData.getRegionCode() == null || csvData.getRegionCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Region code is required");
        }
        
        Region region = regionRepository.findByCode(csvData.getRegionCode());
        if (region == null) {
            throw new ResourceNotFoundException("Region with code '" + csvData.getRegionCode() + "' not found");
        }

        // Step 2: Find or create location
        Location location = locationRepository.findByNameRuAndRegionId(csvData.getLocationNameRu(), region.getId());
        if (location == null) {
            location = new Location();
            location.setRegion(region);
            location.setNameRu(csvData.getLocationNameRu());
            location = locationRepository.save(location);
            log.info("Created new location: {} in region: {}", csvData.getLocationNameRu(), csvData.getRegionCode());
        }

        // Step 3: Find or create/update APK
        APK apk = apkRepository.findByDeviceNumber(csvData.getApkDeviceNumber());
        if (apk == null) {
            apk = new APK();
            apk.setLocation(location);
            apk.setDeviceNumber(csvData.getApkDeviceNumber());
            apk.setCertNumber(csvData.getApkCertNumber());
            apk.setCertIssue(csvData.getApkCertFrom());
            apk.setCertExpiry(csvData.getApkCertTo());
            apk = apkRepository.save(apk);
            log.info("Created new APK: {} at location: {}", csvData.getApkDeviceNumber(), csvData.getLocationNameRu());
        } else {
            // Update existing APK
            apk.setLocation(location);
            apk.setCertNumber(csvData.getApkCertNumber());
            apk.setCertIssue(csvData.getApkCertFrom());
            apk.setCertExpiry(csvData.getApkCertTo());
            apk = apkRepository.save(apk);
            log.info("Updated APK: {} at location: {}", csvData.getApkDeviceNumber(), csvData.getLocationNameRu());
        }

        // Step 4: Find or create/update camera
        Camera camera = null;
        if (csvData.getCameraCode() != null && !csvData.getCameraCode().trim().isEmpty()) {
            camera = cameraRepository.findByCode(csvData.getCameraCode());
        }
        if (camera == null && csvData.getCameraIp() != null && !csvData.getCameraIp().trim().isEmpty()) {
            camera = cameraRepository.findByIp(csvData.getCameraIp());
        }

        if (camera == null) {
            camera = new Camera();
            camera.setApk(apk);
            camera.setIp(csvData.getCameraIp());
            camera.setCode(csvData.getCameraCode());
            camera.setDirection(csvData.getCameraDirection());
            camera.setName("Camera-" + csvData.getCameraCode()); // Default name
            camera = cameraRepository.save(camera);
            log.info("Created new camera: {} for APK: {}", 
                    csvData.getCameraCode() != null ? csvData.getCameraCode() : csvData.getCameraIp(), 
                    csvData.getApkDeviceNumber());
        } else {
            // Update existing camera
            camera.setApk(apk);
            camera.setIp(csvData.getCameraIp());
            camera.setCode(csvData.getCameraCode());
            camera.setDirection(csvData.getCameraDirection());
            camera = cameraRepository.save(camera);
            log.info("Updated camera: {} for APK: {}", 
                    csvData.getCameraCode() != null ? csvData.getCameraCode() : csvData.getCameraIp(), 
                    csvData.getApkDeviceNumber());
        }
    }
}
