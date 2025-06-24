package com.example.SecureShots.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    // ✅ Change this from 'url' to 'publicId'
    private String publicId;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;
}
