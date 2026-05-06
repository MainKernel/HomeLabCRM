package com.homelab.suit.inventory.service;

import com.homelab.suit.inventory.model.Item;
import com.homelab.suit.inventory.model.ItemDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${app.item.documents.upload.dir}")
    private String uploadDocumentsDir;
    @Value("${app.item.preview.upload.dir}")
    private String uploadImagePreviewDir;


    public String storePreviewImage(MultipartFile file) throws IOException {
        Path root = Paths.get(uploadImagePreviewDir);
        Files.createDirectories(root);

        String originalFilename = StringUtils
                .cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.jpg");
        String fileName = UUID.randomUUID() + "_" + originalFilename;
        Path targetPath = root.resolve(fileName);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public List<ItemDocument> saveDocuments(List<MultipartFile> files, Item documentItem) throws IOException {
        List<ItemDocument> savedDocuments = new ArrayList<>();
        Path root = Paths.get(uploadDocumentsDir);
        Files.createDirectories(root);

        for(MultipartFile file : files){
            String originalFilename = StringUtils
                    .cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "document");
            UUID fileUuid = UUID.randomUUID();
            String fileName = fileUuid + "_" + originalFilename;
            Path targetPath = root.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            ItemDocument itemDocument = ItemDocument.builder()
                    .item(documentItem)
                    .fileUrl(fileName)
                    .title(file.getOriginalFilename() != null ? file.getOriginalFilename() : "document")
                    .fileUuid(fileUuid.toString())
                    .documentType(file.getContentType())
                    .build();
            savedDocuments.add(itemDocument);
        }

        return savedDocuments;
    }
    public void deleteFileFromDisk(String filename) {
        if (filename == null || filename.trim().isEmpty()) return;

        try {
            Path filePath = Paths.get(uploadDocumentsDir).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.err.println("Не вдалося видалити фізичний файл: " + filename);
            e.printStackTrace();
        }
    }


}