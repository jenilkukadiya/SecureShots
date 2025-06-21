package com.example.SecureShots.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryDTO {
    private Long id;
    private String title;
    private String description;
    private List<String> photoUrls;
}
