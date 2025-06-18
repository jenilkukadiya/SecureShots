package com.example.SecureShots.controller;

import com.example.SecureShots.dto.GalleryDTO;
import com.example.SecureShots.model.Gallery;
import com.example.SecureShots.model.Photo;
import com.example.SecureShots.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping("/create/{email}")
    public ResponseEntity<Gallery> createGallery(@PathVariable String email,
                                                 @RequestBody GalleryDTO dto) {
        return ResponseEntity.ok(galleryService.createGallery(email, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Photo>> viewPhotos(@PathVariable Long id,
                                                  @RequestParam String password) {
        return ResponseEntity.ok(galleryService.getPhotos(id, password));
    }
}
