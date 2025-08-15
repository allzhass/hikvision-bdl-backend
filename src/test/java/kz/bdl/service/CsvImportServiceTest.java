package kz.bdl.service;

import kz.bdl.entity.Region;
import kz.bdl.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CsvImportServiceTest {

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    void testImportCsvData_Success() {
        // Create a test region first
        Region region = new Region();
        region.setCode("ALM");
        region.setNameRu("Алматы");
        region = regionRepository.save(region);

        // Create CSV content
        String csvContent = "region_code;location_name_ru;apk_device_number;apk_cert_number;apk_cert_from;apk_cert_to;camera_ip;camera_code;camera_direction\n" +
                "ALM;Алматы центр;APK001;CERT001;2024-01-01 00:00:00;2025-01-01 00:00:00;192.168.1.100;CAM001;Север";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes()
        );

        // Execute import
        String result = csvImportService.importCsvData(file);

        // Verify result
        assertNotNull(result);
        assertTrue(result.contains("Import completed"));
        assertTrue(result.contains("Success: 1"));
        assertTrue(result.contains("Errors: 0"));
    }

    @Test
    void testImportCsvData_RegionNotFound() {
        // Create CSV content with non-existent region
        String csvContent = "region_code;location_name_ru;apk_device_number;apk_cert_number;apk_cert_from;apk_cert_to;camera_ip;camera_code;camera_direction\n" +
                "NONEXISTENT;Алматы центр;APK001;CERT001;2024-01-01 00:00:00;2025-01-01 00:00:00;192.168.1.100;CAM001;Север";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes()
        );

        // Execute import
        String result = csvImportService.importCsvData(file);

        // Verify result
        assertNotNull(result);
        assertTrue(result.contains("Import completed"));
        assertTrue(result.contains("Success: 0"));
        assertTrue(result.contains("Errors: 1"));
        assertTrue(result.contains("Region with code 'NONEXISTENT' not found"));
    }

    @Test
    void testImportCsvData_InvalidFormat() {
        // Create CSV content with invalid format
        String csvContent = "region_code,location_name_ru,apk_device_number\n" +
                "ALM,Алматы центр,APK001";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes()
        );

        // Execute import
        String result = csvImportService.importCsvData(file);

        // Verify result
        assertNotNull(result);
        assertTrue(result.contains("Import completed"));
        assertTrue(result.contains("Success: 0"));
        assertTrue(result.contains("Errors: 1"));
        assertTrue(result.contains("Invalid CSV format"));
    }
}
