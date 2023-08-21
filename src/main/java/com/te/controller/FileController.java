package com.te.controller;

import com.te.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(path = "/file")
@RestController
public class FileController {

    private final AmazonS3Service amazonS3Service;

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String bucketName = "pocsimples3bucket";
        String keyName = file.getOriginalFilename();
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        return amazonS3Service.uploadFile(bucketName, keyName, tempFile);
    }

    @GetMapping(path = "/access")
    public String accessPrivateFile(@RequestParam String fileName) {
        System.out.println(fileName);
        String bucketName = "pocsimples3bucket";
        if (amazonS3Service.checkIfExistInS3(bucketName, fileName)) {
            return amazonS3Service.accessPrivateFile(bucketName, fileName);
        }
        return "FILE DOES NOT EXIST";
    }

    @GetMapping(path = "/check")
    public boolean checkIfExistInS3(@RequestParam String fileName) {
        String bucketName = "pocsimples3bucket";
        return amazonS3Service.checkIfExistInS3(bucketName, fileName);
    }
}
