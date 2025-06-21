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
    private String url;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;
}
