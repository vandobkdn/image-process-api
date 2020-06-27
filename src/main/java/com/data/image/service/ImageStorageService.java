package com.data.image.service;

import com.data.image.models.EntityImageUrl;
import com.data.image.task.DownloadImageTask;
import com.data.image.task.ManipulateDirectoryTask;
import com.data.image.task.UploadImageFromLocalTask;
import com.data.image.task.UploadImageTask;
import com.data.image.uitls.Constants;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ImageStorageService {

    public void downloadImage(EntityImageUrl imageUrl) throws IOException, InterruptedException {
        List<String> imageUrls = imageUrl.getImageUrls();
        String entity = imageUrl.getEntity();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < imageUrls.size(); i++) {
            executor.execute(new DownloadImageTask(imageUrls.get(i), entity, i));
        }
        System.out.println("Finished download!!");
    }

    public void uploadImage(EntityImageUrl imageUrl) throws IOException, InterruptedException {
        List<String> imageUrls = imageUrl.getImageUrls();
        String entity = imageUrl.getEntity();
        ExecutorService executor = Executors.newCachedThreadPool();
        ManipulateDirectoryTask manipulator = ManipulateDirectoryTask.getInstance();
        for (int i = 0; i< imageUrls.size(); i++) {
            String targetPath = manipulator.createCloudObject(entity, manipulator.createImageName(entity, i));
            executor.execute(new UploadImageTask(imageUrls.get(i), targetPath));
        }
        System.out.println("Finished upload!!");
    }

    public void uploadImageFromLocal(EntityImageUrl imageUrl) throws IOException {
        String entity = imageUrl.getEntity();
        ManipulateDirectoryTask manipulator = ManipulateDirectoryTask.getInstance();
        File[] listOfFiles = manipulator.getFiles(Paths.get(Constants.DOWNLOAD_PATH, entity.substring(0, 1), entity).toString());
        ExecutorService executor = Executors.newCachedThreadPool();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String filePath = Paths.get(Constants.DOWNLOAD_PATH, entity.substring(0, 1), entity, file.getName()).toString();
                String targetPath = manipulator.createCloudObject(entity, file.getName());
                executor.execute(new UploadImageFromLocalTask(filePath, targetPath));
            }
        }
//        deleteDirectory(Paths.get(Constants.DOWNLOAD_PATH, entity.substring(0, 1), entity).toString());
    }
}
