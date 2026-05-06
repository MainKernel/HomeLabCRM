package com.homelab.suit.inventory.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController("/api/inventory/resources")
public class ResourceController {

    @Value("${app.item.documents.upload.dir}")
    private String uploadDocumentsDir;
    @Value("${app.item.preview.upload.dir}")
    private String uploadImagePreviewDir;

    private  Path previewStorage;

    @PostConstruct
    public void init(){
        this.previewStorage  = Paths.get(uploadImagePreviewDir).toAbsolutePath().normalize();
    }

    @GetMapping("/preview/{fileName:.+}")
    public ResponseEntity<Resource> getPreview(@PathVariable String fileName){
        try {
            Path filePath = this.previewStorage.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
