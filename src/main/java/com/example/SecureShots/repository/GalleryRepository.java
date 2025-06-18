package com.example.SecureShots.repository;

import com.example.SecureShots.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Optional<Gallery> findByIdAndPassword(Long id, String password);
}
