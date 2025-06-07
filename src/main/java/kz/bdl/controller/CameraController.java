package kz.bdl.controller;

import kz.bdl.dto.CameraDTO;
import kz.bdl.service.CameraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camera")
@Slf4j
public class CameraController {
    @Autowired
    private CameraService cameraService;

    @GetMapping("/{id}")
    public CameraDTO getCameraById(@PathVariable Long id) {
        log.info("getCameraById start: {}", id);
        CameraDTO result = cameraService.getCameraById(id);
        log.info("getCameraById end");
        return result;
    }

    @GetMapping
    public List<CameraDTO> getAllCamera() {
        log.info("getAllCamera start");
        List<CameraDTO> result = cameraService.getAllCamera();
        log.info("getAllCamera end");
        return result;
    }

    @GetMapping("/apk/{apkId}")
    public List<CameraDTO> getCameraByAPKId(@PathVariable Long apkId) {
        log.info("getCameraByAPKId start: {}", apkId);
        List<CameraDTO> result = cameraService.getCameraByAPKId(apkId);
        log.info("getCameraByAPKId end");
        return result;
    }

    @PostMapping
    public ResponseEntity<String> addCamera(@RequestBody CameraDTO cameraDTO) {
        log.info("addCamera start: {}", cameraDTO);
        ResponseEntity<String> result = cameraService.addCamera(cameraDTO);
        log.info("addCamera end");
        return result;
    }

    @PutMapping
    public ResponseEntity<String> changeCamera(@RequestBody CameraDTO cameraDTO) {
        log.info("changeCamera start: {}", cameraDTO);
        ResponseEntity<String> result = cameraService.changeCamera(cameraDTO);
        log.info("changeCamera end");
        return result;
    }
}