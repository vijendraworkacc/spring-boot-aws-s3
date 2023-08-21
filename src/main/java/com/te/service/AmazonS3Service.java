package com.te.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class AmazonS3Service {
    private final AmazonS3 amazonS3;

    public String uploadFile(String bucketName, String keyName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file));
        return amazonS3.getUrl(bucketName, keyName).toString();
    }

    public String accessPrivateFile(String bucketName, String fileName) {
        return amazonS3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, fileName).withMethod(HttpMethod.GET).withExpiration(new Date(System.currentTimeMillis() + 3600000))).toString();
    }

    public boolean checkIfExistInS3(String bucketName, String fileName) {
        return amazonS3.doesObjectExist(bucketName, fileName);
    }
}
