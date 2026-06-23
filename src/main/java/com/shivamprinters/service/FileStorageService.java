package com.shivamprinters.service;

import com.shivamprinters.config.AppProperties;
import com.shivamprinters.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    private final Path uploadPath;

    public FileStorageService(AppProperties appProperties) {
        this.uploadPath = Paths.get(appProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException ex) {
            throw new BusinessException("Could not create upload directory");
        }
    }

    public String store(MultipartFile file, String subDirectory) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("File is empty");
        }
        String originalName = file.getOriginalFilename();
        String extension = originalName != null && originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf('.'))
                : "";
        String fileName = UUID.randomUUID() + extension;
        try {
            Path targetDir = uploadPath.resolve(subDirectory);
            Files.createDirectories(targetDir);
            Path targetPath = targetDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Stored file: {}", targetPath);
            return "/uploads/" + subDirectory + "/" + fileName;
        } catch (IOException ex) {
            log.error("Failed to store file", ex);
            throw new BusinessException("Failed to store file");
        }
    }
}
