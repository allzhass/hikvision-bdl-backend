package kz.bdl.service;

import org.springframework.web.multipart.MultipartFile;

public interface DataImportService {
    String importXlsxData(MultipartFile file);
}
