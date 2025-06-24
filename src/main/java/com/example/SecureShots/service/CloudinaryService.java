package com.example.SecureShots.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "type", "authenticated",  // 👈 set delivery type to authenticated
                        "resource_type", "image"
                )
        );

        // Save only the public ID (not URL) for future secure access
        return uploadResult.get("public_id").toString();
    }

    public String generateSecureUrl(String publicId) {
        return cloudinary.url()
                .signed(true)
                .type("authenticated")
                .secure(true)
                .generate(publicId);
    }
}

