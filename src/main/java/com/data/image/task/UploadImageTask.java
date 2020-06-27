package com.data.image.task;

import com.data.image.uitls.Constants;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UploadImageTask implements Runnable {
    private String targetPath;
    private String imageUrl;

    public UploadImageTask(String imageUrl, String targetPath) {
        this.imageUrl = imageUrl;
        this.targetPath = targetPath;
    }

    @Override
    public void run() {
        try {
            uploadImage(imageUrl, targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(String imageUrl, String targetPath) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(Constants.PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(Constants.BUCKET_NAME, targetPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        InputStream inputStream = new URL(imageUrl).openStream();
        storage.create(blobInfo, IOUtils.toByteArray(inputStream));
    }
}
