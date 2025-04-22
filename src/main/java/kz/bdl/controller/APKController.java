package kz.bdl.controller;

import kz.bdl.dto.APKDTO;
import kz.bdl.service.APKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apk")
public class APKController {
    @Autowired
    private APKService apkService;

    @GetMapping("/{id}")
    public APKDTO getAPKById(@PathVariable Long id) {
        return apkService.getAPKById(id);
    }

    @GetMapping
    public List<APKDTO> getAllAPK() {
        return apkService.getAllAPK();
    }

    @GetMapping("/location/{locationId}")
    public List<APKDTO> getAPKByLocationId(@PathVariable Long locationId) {
        return apkService.getAPKByLocationId(locationId);
    }

    @GetMapping("/has-camera")
    public List<APKDTO> getAPKHasCamera() {
        return apkService.getAPKHasCamera();
    }

    @PostMapping
    public ResponseEntity<String> addAPK(@RequestBody APKDTO apkDTO) {
        return apkService.addAPK(apkDTO);
    }

    @PutMapping
    public ResponseEntity<String> changeAPK(@RequestBody APKDTO apkDTO) {
        return apkService.changeAPK(apkDTO);
    }
}