package com.data.image.service;

import com.data.image.models.EntityImageUrl;
import com.data.image.threads.DownLoadTask;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ImageStorageService {

    private static Path uploadPath;
    private static Path downloadPath = Paths.get("./images/download");

    public void downloadImage(EntityImageUrl imageUrl) {
        List<String> imageUrls = imageUrl.getImageUrls();
        ExecutorService executor = Executors.newCachedThreadPool();
        imageUrls.forEach(url -> {
            executor.execute(new DownLoadTask(this, url, imageUrl.getEntity()) );
        });
        System.out.println("done!");
    }

    private void createDirectory(String file) {
        File f = new File(file);
        try{
            if(f.mkdirs()) {
                System.out.println("Directory Created");
            } else {
                System.out.println("Directory is not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getImageName(String imageUrl) throws URISyntaxException {
        return Paths.get(new URI(imageUrl).getPath()).getFileName().toString();
    }

    public void save(String imageUrl, String imageName, String entity) {
        String directoryCharacter = "/";
        String directoryPath = Paths.get(downloadPath + directoryCharacter + entity.substring(0, 1) + directoryCharacter + entity).toString();
        createDirectory(directoryPath);
        String destinationFilePath = Paths.get(directoryPath + directoryCharacter + imageName).toString();
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            Files.copy(inputStream, Paths.get(destinationFilePath));
        } catch (IOException e) {
            System.out.println("Exception Occurred " + e);
        }
    }
}
