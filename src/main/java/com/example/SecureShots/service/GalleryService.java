package com.example.SecureShots.service;

import com.example.SecureShots.dto.GalleryDTO;
import com.example.SecureShots.model.Gallery;
import com.example.SecureShots.model.Photo;
import com.example.SecureShots.model.User;
import com.example.SecureShots.repository.GalleryRepository;
import com.example.SecureShots.repository.PhotoRepository;
import com.example.SecureShots.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Gallery createGallery(String email, GalleryDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Gallery gallery = Gallery.builder()
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .createdBy(user)
                .build();

        return galleryRepository.save(gallery);
    }

    public List<Photo> getPhotos(Long galleryId, String password) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));

        if (!passwordEncoder.matches(password, gallery.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return photoRepository.findByGalleryId(galleryId);
    }

    // Add upload logic later with AWS S3
}
