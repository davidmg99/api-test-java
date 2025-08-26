package com.dmartinez.api.service.file.impl;

import com.dmartinez.api.service.file.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {
    private final Path storageFolder = Paths.get("invoices");

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String storedFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.createDirectories(storageFolder);
        Path destination = storageFolder.resolve(storedFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return storedFileName;
    }
}
