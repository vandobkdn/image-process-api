package com.data.image.controller;

import com.data.image.models.EntityImageUrl;
import com.data.image.service.ImageStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value = "api/images")
public class DownloadImageController {

    private ImageStorageService imageStorageService;

    public DownloadImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping(value = "/download")
    public ResponseEntity<Void> downloadImage(@RequestBody EntityImageUrl entityImageUrl) throws IOException {
        imageStorageService.downloadImage(entityImageUrl);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
