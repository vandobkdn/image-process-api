package com.data.image.task;

import com.data.image.uitls.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadImageTask implements Runnable {
    private String imageUrl;
    private String entity;
    private int index;

    public DownloadImageTask(String imageUrl, String entity, int index) {
        this.imageUrl = imageUrl;
        this.entity = entity;
        this.index = index;
    }

    @Override
    public void run() {
        try {
            download(imageUrl, entity, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadAndSaveImage(String imageUrl, String storageFilePath) {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            Files.copy(inputStream, Paths.get(storageFilePath));
        } catch (IOException e) {
            System.out.println("Exception Occurred " + e);
        }
    }

    private void download(String imageUrl, String entity, int index) {
        String directoryPath = Paths.get(Constants.DOWNLOAD_PATH, entity.substring(0, 1), entity).toString();
        ManipulateDirectoryTask manipulator = ManipulateDirectoryTask.getInstance();
        manipulator.createDirectory(directoryPath);
        String imageName = manipulator.createImageName(entity, index);
        String storageFilePath = Paths.get(directoryPath, imageName).toString();
        downloadAndSaveImage(imageUrl, storageFilePath);
    }
}
