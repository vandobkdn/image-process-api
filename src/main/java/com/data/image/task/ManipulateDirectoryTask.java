package com.data.image.task;

import com.data.image.uitls.Constants;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ManipulateDirectoryTask {
    private static ManipulateDirectoryTask instance = null;

    private ManipulateDirectoryTask() {}

    public static ManipulateDirectoryTask getInstance() {
        if (instance == null) {
            instance = new ManipulateDirectoryTask();
        }
        return instance;
    }

    public final void createDirectory(String filePath) {
        File f = new File(filePath);
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

    public final void deleteDirectory(String destination) throws IOException {
        FileUtils.deleteDirectory(new File(destination));
    }

    public final String createImageName(String entity, int index) {
        return entity + Constants.HYPHEN + index + ".jpg";
    }

    public File[] getFiles(String folderPath) throws IOException {
        File folder = new File(folderPath);
        return folder.listFiles();
    }

    public final String createCloudObject(String entity, String fileName) {
        return Paths.get(Constants.RAW_IMAGES_PATH, entity.substring(0, 1), entity, fileName).toString();
    }
}
