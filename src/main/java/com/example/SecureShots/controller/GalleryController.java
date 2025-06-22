package com.example.SecureShots.controller;

import com.example.SecureShots.dto.GalleryDTO;
import com.example.SecureShots.model.Gallery;
import com.example.SecureShots.model.Photo;
import com.example.SecureShots.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/create")
    public ResponseEntity<Gallery> createGallery(@RequestBody Gallery gallery, Authentication auth) {
        return ResponseEntity.ok(galleryService.createGallery(gallery, auth.getName()));
    }

//    @PostMapping("/{galleryId}/photo")
//    public ResponseEntity<Void> addPhoto(@PathVariable Long galleryId, @RequestBody Photo photo) {
//        galleryService.addPhotoToGallery(galleryId, photo);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/{galleryId}/photo")
    public ResponseEntity<Void> addPhoto(@PathVariable Long galleryId,
                                         @RequestParam("file") MultipartFile file) throws IOException {
        galleryService.addPhotoToGallery(galleryId, file);  // Let service handle upload and saving
        return ResponseEntity.ok().build();
    }




    @GetMapping("/public/{galleryId}")
    public ResponseEntity<GalleryDTO> getGallery(@PathVariable Long galleryId, @RequestParam String password) {
        Optional<GalleryDTO> gallery = galleryService.getPublicGallery(galleryId, password);
        return gallery.map(ResponseEntity::ok).orElse(ResponseEntity.status(403).build());
    }
}