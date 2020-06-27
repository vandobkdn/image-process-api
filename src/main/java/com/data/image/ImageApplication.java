package com.data.image;

import com.data.image.models.EntityImageUrl;
import com.data.image.service.EntityImageUrlService;
import com.data.image.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ImageApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImageApplication.class, args);
    }

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private EntityImageUrlService entityImageUrlService;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        EntityImageUrl entityImageUrl = entityImageUrlService.findOne("anno-domini");

        imageStorageService.downloadImage(entityImageUrl);
        System.out.println("All done: ");
//        imageStorageService.uploadImage("lingbook-api-dev", "lingbook-dev-raw", "b/balletic/", "./images/download/b/balletic");
    }
}
