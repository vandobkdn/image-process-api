package com.data.image.threads;

import com.data.image.service.ImageStorageService;
import java.net.URISyntaxException;

public class DownLoadTask implements Runnable {
    private ImageStorageService service;
    private final String url;
    private final String entity;

    public DownLoadTask(ImageStorageService service, String url, String entity) {
        this.service = service;
        this.url = url;
        this.entity = entity;
    }

    @Override
    public void run() {
        try {
            service.save(url, service.getImageName(url) , entity);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
