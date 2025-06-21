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

    public Gallery createGallery(Gallery gallery, String email) {
        User owner = userRepository.findByEmail(email).orElseThrow();
        gallery.setOwner(owner);
        return galleryRepository.save(gallery);
    }

    public void addPhotoToGallery(Long galleryId, Photo photo) {
        Gallery gallery = galleryRepository.findById(galleryId).orElseThrow();
        photo.setGallery(gallery);
        photoRepository.save(photo);
    }

    public Optional<GalleryDTO> getPublicGallery(Long galleryId, String password) {
        Optional<Gallery> galleryOpt = galleryRepository.findByIdAndAccessPassword(galleryId, password);
        return galleryOpt.map(gallery -> new GalleryDTO(
                gallery.getId(),
                gallery.getTitle(),
                gallery.getDescription(),
                gallery.getPhotos().stream().map(Photo::getUrl).collect(Collectors.toList())
        ));
    }
}