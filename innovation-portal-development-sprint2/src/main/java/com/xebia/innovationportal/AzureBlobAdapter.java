package com.xebia.innovationportal;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.xebia.innovationportal.constants.CommonConstants;

@Component
public class AzureBlobAdapter {

    @Autowired
    private CloudBlobContainer cloudBlobContainer;

    public String upload(String uploadDir, String uploadedFile) {
        CloudBlockBlob blob = null;
        try {

            File sourceFile = new File(uploadDir + "/" + uploadedFile);
            blob = cloudBlobContainer.getBlockBlobReference(sourceFile.getName());
            blob.uploadFromFile(sourceFile.getAbsolutePath());
            String url = blob.getUri().toString();
            return url;
        } catch (IOException | URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getFileFromAzyur(String fileName, long userId) {
        CloudBlob blob = null;
        try {
            blob = cloudBlobContainer.getBlobReferenceFromServer(fileName);
            String file = Paths.get(CommonConstants.DIRECTORY_LOCATION + "/" + userId + "/" + fileName).toAbsolutePath()
                    .toString();

            blob.downloadToFile(file);
            return true;
        } catch (IOException | URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
        return false;

    }

}
