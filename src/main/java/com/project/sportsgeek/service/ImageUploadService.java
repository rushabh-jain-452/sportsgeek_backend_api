package com.project.sportsgeek.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageUploadService {

    public File uploadImage(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
//            System.out.println("Filename : " + fileName);
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();
//            System.out.println("File:"+file);// to delete the copy of uploaded file stored in the project folder// Your customized response
            return file;
        } catch (Exception e) {
//            e.printStackTrace();
//            File ex_file = new File("");
//            return ex_file;
            return null;
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private void uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("sportsgeek-74e1e.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("sportsgeek-74e1e-firebase-adminsdk-4s62v-7cc67b989e.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        return String.format("https://firebasestorage.googleapis.com/v0/b/sportsgeek-74e1e.appspot.com/o/", URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)));
    }

}
