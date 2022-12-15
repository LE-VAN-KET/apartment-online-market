package com.cdcn.apartmentonlinemarket.products.controller;

import com.cdcn.apartmentonlinemarket.common.util.MinioUtil;
import com.cdcn.apartmentonlinemarket.products.services.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")

public class ImageController {
//    private final FilesStorageService filesStorageService;

    @Autowired
    private MinioUtil minioUtil;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String filename) {
//        Resource file = filesStorageService.load(filename);
        byte[] data = minioUtil.getFile(filename);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
