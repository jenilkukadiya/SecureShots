package com.example.SecureShots.service;

import com.example.SecureShots.dto.GalleryDTO;
import com.example.SecureShots.model.Gallery;
import com.example.SecureShots.model.Photo;
import com.example.SecureShots.model.User;
import com.example.SecureShots.repository.GalleryRepository;
import com.example.SecureShots.repository.PhotoRepository;
import com.example.SecureShots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private CloudinaryService cloudinaryService;

    public Gallery createGallery(Gallery gallery, String email) {
        User owner = userRepository.findByEmail(email).orElseThrow();
        gallery.setOwner(owner);
        return galleryRepository.save(gallery);
    }

//    public void addPhotoToGallery(Long galleryId, Photo photo) {
//        Gallery gallery = galleryRepository.findById(galleryId).orElseThrow();
//        photo.setGallery(gallery);
//        photoRepository.save(photo);
//    }

//    public void addPhotoToGallery(Long galleryId, MultipartFile file) throws IOException {
//        Gallery gallery = galleryRepository.findById(galleryId)
//                .orElseThrow(() -> new RuntimeException("Gallery not found"));
//
//        String s3Url = s3Service.uploadFile(file);
//
//        Photo photo = Photo.builder()
//                .fileName(file.getOriginalFilename())
//                .url(s3Url)
//                .gallery(gallery)
//                .build();
//
//        photoRepository.save(photo);
//    }


    public void addPhotoToGallery(Long galleryId, MultipartFile file) throws IOException {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));

        String publicId = cloudinaryService.uploadFile(file);  // Save public ID instead of full URL

        Photo photo = Photo.builder()
                .fileName(file.getOriginalFilename())
                .publicId(publicId)  // storing publicId
                .gallery(gallery)
                .build();

        photoRepository.save(photo);
    }

    public Optional<GalleryDTO> getPublicGallery(Long galleryId, String password) {
        Optional<Gallery> galleryOpt = galleryRepository.findByIdAndAccessPassword(galleryId, password);
        return galleryOpt.map(gallery -> new GalleryDTO(
                gallery.getId(),
                gallery.getTitle(),
                gallery.getDescription(),
                gallery.getPhotos().stream()
                        .map(photo -> cloudinaryService.generateSecureUrl(photo.getPublicId())) // signed URL
                        .collect(Collectors.toList())
        ));
    }

}