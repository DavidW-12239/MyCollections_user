package com.collections.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class Utils {
    private static final String UPLOAD_DIR = "E:\\React Project\\my-app\\public\\collectionImages";

    public static String uploadImage(MultipartFile image) throws IOException {
        String imagePath = "\\default.jpg";
        if (image != null) {
            String fileName = StringUtils.cleanPath(UUID.randomUUID() + image.getOriginalFilename());
            Path uploadDir = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = fileName;
            } catch (IOException e) {
                throw new RuntimeException("Could not save image: " + e.getMessage());
            }
        }
        return imagePath;

    };
}
