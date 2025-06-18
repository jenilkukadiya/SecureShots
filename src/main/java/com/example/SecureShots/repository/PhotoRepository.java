package com.example.SecureShots.repository;

import com.example.SecureShots.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByGalleryId(Long galleryId);
}
