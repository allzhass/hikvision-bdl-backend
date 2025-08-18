package kz.bdl.service.impl;

import kz.bdl.dto.FileDataImportDTO;
import kz.bdl.entity.APK;
import kz.bdl.entity.Camera;
import kz.bdl.entity.Location;
import kz.bdl.entity.Region;
import kz.bdl.exception.ResourceNotFoundException;
import kz.bdl.repository.APKRepository;
import kz.bdl.repository.CameraRepository;
import kz.bdl.repository.LocationRepository;
import kz.bdl.repository.RegionRepository;
import kz.bdl.service.DataImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@Slf4j
public class DataImportServiceImpl implements DataImportService {

    @Autowired private RegionRepository regionRepository;
    @Autowired private LocationRepository locationRepository;
    @Autowired private APKRepository apkRepository;
    @Autowired private CameraRepository cameraRepository;

    private void processFileData(FileDataImportDTO fileData) {
        log.info("Processing XLSX data: {}", fileData);
        
        if (fileData.getRegionCode() == null || fileData.getRegionCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Region code is required");
        }
        
        Region region = regionRepository.findByCode(fileData.getRegionCode());
        if (region == null) {
            throw new ResourceNotFoundException("Region with code '" + fileData.getRegionCode() + "' not found");
        }

        Location location = locationRepository.findByNameRuAndRegionId(fileData.getLocationNameRu(), region.getId());
        if (location == null) {
            location = new Location();
            location.setRegion(region);
            location.setNameRu(fileData.getLocationNameRu());
            location = locationRepository.save(location);
            log.info("Created new location: {} in region: {}", fileData.getLocationNameRu(), fileData.getRegionCode());
        }

        APK apk = apkRepository.findByDeviceNumber(fileData.getApkDeviceNumber());
        if (apk == null) {
            apk = new APK();
            apk.setLocation(location);
            apk.setDeviceNumber(fileData.getApkDeviceNumber());
            apk.setCertNumber(fileData.getApkCertNumber());
            apk.setCertIssue(fileData.getApkCertFrom());
            apk.setCertExpiry(fileData.getApkCertTo());
            apk = apkRepository.save(apk);
            log.info("Created new APK: {} at location: {}", fileData.getApkDeviceNumber(), fileData.getLocationNameRu());
        } else {
            apk.setLocation(location);
            apk.setCertNumber(fileData.getApkCertNumber());
            apk.setCertIssue(fileData.getApkCertFrom());
            apk.setCertExpiry(fileData.getApkCertTo());
            apk = apkRepository.save(apk);
            log.info("Updated APK: {} at location: {}", fileData.getApkDeviceNumber(), fileData.getLocationNameRu());
        }

        Camera camera = null;
        if (fileData.getCameraCode() != null && !fileData.getCameraCode().trim().isEmpty()) {
            camera = cameraRepository.findByCode(fileData.getCameraCode());
        }
        if (camera == null && fileData.getCameraIp() != null && !fileData.getCameraIp().trim().isEmpty()) {
            camera = cameraRepository.findByIp(fileData.getCameraIp());
        }

        if (camera == null) {
            camera = new Camera();
            camera.setApk(apk);
            camera.setIp(fileData.getCameraIp());
            camera.setCode(fileData.getCameraCode());
            camera.setDirection(fileData.getCameraDirection());
            camera.setName("Camera-" + fileData.getCameraCode()); // Default name
            camera = cameraRepository.save(camera);
            log.info("Created new camera: {} for APK: {}", 
                    fileData.getCameraCode() != null ? fileData.getCameraCode() : fileData.getCameraIp(), 
                    fileData.getApkDeviceNumber());
        } else {
            camera.setApk(apk);
            camera.setIp(fileData.getCameraIp());
            camera.setCode(fileData.getCameraCode());
            camera.setDirection(fileData.getCameraDirection());
            camera = cameraRepository.save(camera);
            log.info("Updated camera: {} for APK: {}", 
                    fileData.getCameraCode() != null ? fileData.getCameraCode() : fileData.getCameraIp(), 
                    fileData.getApkDeviceNumber());
        }
    }

    @Override
    public String importXlsxData(MultipartFile file) {

        log.info("Starting XLSX import for file: {}", file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            Map<Integer, FileDataImportDTO> data = new HashMap<>();
            int i = 0;
            ROW: for (Row row : sheet) {
                if (i == 0) {
                    i++;
                    continue;
                }

                int j = 0;
                data.put(i, new FileDataImportDTO(sheet.getSheetName()));
                for (Cell cell : row) {
                    System.out.println("rowIndex: " + i + ", index: " + j + ", VALUE: " + cell);
                    if (j == 1) {
                        if (cell.getCellType() == CellType.BLANK) {
                            j++;
                            continue ROW;
                        }
                        data.get(i).setCameraIp(String.valueOf(cell));
                    }
                    if (j == 2) {
                        data.get(i).setCameraCode(String.valueOf(cell));
                    }
                    if (j == 3) {
                        data.get(i).setApkDeviceNumber(String.valueOf(cell));
                    }
                    if (j == 4) {
                        data.get(i).setApkCertNumber(String.valueOf(cell));
                    }
                    if (j == 5) {
                        data.get(i).setApkCertFrom(cell.getLocalDateTimeCellValue());
                    }
                    if (j == 6) {
                        data.get(i).setApkCertTo(cell.getLocalDateTimeCellValue());
                    }
                    if (j == 7) {
                        data.get(i).setLocationNameRu(String.valueOf(cell));
                    }
                    if (j == 8) {
                        data.get(i).setCameraDirection(String.valueOf(cell));
                    }
                    j++;
                }
                i++;
            }

            for (Map.Entry<Integer, FileDataImportDTO> entry : data.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                processFileData(entry.getValue());
            }

            inputStream.close();
        } catch (Exception e) {
            log.error("Error importing XLSX file: {}", e.getMessage());
            throw new RuntimeException("Error importing XLSX file: " + e.getMessage());
        }

        return "XLSX import completed";
    }
}
