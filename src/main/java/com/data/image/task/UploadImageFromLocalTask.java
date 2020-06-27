package com.data.image.task;

import com.data.image.uitls.Constants;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadImageFromLocalTask implements Runnable {

    private String targetPath;
    private String filePath;

    public UploadImageFromLocalTask(String filePath, String targetPath) {
        this.targetPath = targetPath;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            uploadImage(targetPath, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(String targetPath, String filePath) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(Constants.PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(Constants.BUCKET_NAME, targetPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
    }
}
