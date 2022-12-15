package com.cdcn.apartmentonlinemarket.common.util;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtil {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        createBucket(this.bucketName);
    }

    /**
     * Create Bucket
     * @param bucketName Bucket Name */
    @SneakyThrows(Exception.class)
    public void createBucket(String bucketName) {
        boolean isExist = bucketExists(bucketName);
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * Determine whether the bucket exists
     * */
    @SneakyThrows(Exception.class)
    public boolean bucketExists(String _bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(_bucketName).build());
    }

    public void uploadFile(MultipartFile file) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
            String fileName = file.getOriginalFilename();
            file.getInputStream().close();
//            return getFileUrl(this.bucketName, fileName);
        } catch (Exception e) {
            log.error("Happened error when upload file: ", e);
            throw new RuntimeException("Upload file error");
        }
    }

    @SneakyThrows(Exception.class)
    public String getFileUrl(String bucketName, String fileName) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName).method(Method.GET)
                        .object(fileName).build());
    }

    public byte[] getFile(String fileName) {
        try {
            InputStream obj = minioClient.getObject(GetObjectArgs.builder().bucket(this.bucketName)
                    .object(fileName).build());

            byte[] content = IOUtils.toByteArray(obj);
            obj.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete file
     * @param bucketName Bucket Name
     * @param fileName File Name
     * @return void*/
    @SneakyThrows(Exception.class)
    public void deleteFile(String bucketName, String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

}

