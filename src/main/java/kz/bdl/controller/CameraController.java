package kz.bdl.controller;

import kz.bdl.dto.CameraDTO;
import kz.bdl.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camera")
public class CameraController {
    @Autowired
    private CameraService cameraService;

    @GetMapping("/{id}")
    public CameraDTO getCameraById(@PathVariable Long id) {
        return cameraService.getCameraById(id);
    }

    @GetMapping
    public List<CameraDTO> getAllCamera() {
        return cameraService.getAllCamera();
    }

    @GetMapping("/apk/{apkId}")
    public List<CameraDTO> getCameraByAPKId(@PathVariable Long apkId) {
        return cameraService.getCameraByAPKId(apkId);
    }

    @PostMapping
    public ResponseEntity<String> addCamera(@RequestBody CameraDTO cameraDTO) {
        return cameraService.addCamera(cameraDTO);
    }

    @PutMapping
    public ResponseEntity<String> changeCamera(@RequestBody CameraDTO cameraDTO) {
        return cameraService.changeCamera(cameraDTO);
    }
}