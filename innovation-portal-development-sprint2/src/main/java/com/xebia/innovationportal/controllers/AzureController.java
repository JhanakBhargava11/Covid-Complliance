package com.xebia.innovationportal.controllers;

import static com.xebia.innovationportal.constants.RestConstants.INNOVATION_PORTAL_API;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEAS_DOWNLOAD;
import static com.xebia.innovationportal.constants.RestConstants.ideaDetails.IDEAS_UPLOAD;
import static com.xebia.innovationportal.services.IdeaService.getUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xebia.innovationportal.AzureBlobAdapter;
import com.xebia.innovationportal.constants.CommonConstants;

@RestController
@RequestMapping(value = INNOVATION_PORTAL_API)
public class AzureController {

    @Autowired
    private AzureBlobAdapter azureBlobAdapter;

    @PostMapping(IDEAS_UPLOAD)
    public ResponseEntity<?> upload(@RequestParam MultipartFile file) {
        String url = "";
        String[] response = new String[2];
        Long userId = getUser().getId();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String dirPath = CommonConstants.DIRECTORY_LOCATION + "/" + userId;
        File uploadPath = Paths.get(CommonConstants.DIRECTORY_LOCATION + "/" + userId + "/").toAbsolutePath().toFile();
        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileNameWithTimeStamp = fileName + "_" + timestamp.getTime() + extension;
        File uploadFile = new File(uploadPath.getAbsolutePath(), fileNameWithTimeStamp);
        uploadFile(file, uploadFile);
        if (uploadFile.exists()) {
            url = azureBlobAdapter.upload(dirPath, fileNameWithTimeStamp);
            try {
                FileUtils.cleanDirectory(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response[0] = fileNameWithTimeStamp;
        response[1] = url;
        return ResponseEntity.ok(response);
    }

    @GetMapping(IDEAS_DOWNLOAD)
    public ResponseEntity<?> download(@RequestParam("fileName") String fileName) {
        Long userId = getUser().getId();
        boolean isTrue = azureBlobAdapter.getFileFromAzyur(fileName, userId);
        if (isTrue) {
            Path path = Paths.get(CommonConstants.DIRECTORY_LOCATION + "/" + userId + "/" + fileName).toAbsolutePath();
            UrlResource resource = null;
            try {
                resource = new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        return ResponseEntity.noContent().build();
    }

    private void uploadFile(MultipartFile file, File updatedFile) {
        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(updatedFile)) {
            FileCopyUtils.copy(in, out);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
