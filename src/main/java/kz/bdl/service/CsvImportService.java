package kz.bdl.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvImportService {
    String importCsvData(MultipartFile file);
}
