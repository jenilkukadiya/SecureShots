package com.example.SecureShots.dto;

import lombok.Data;

import java.util.List;

@Data
public class GalleryDTO {
    private String name;
    private String password;
    private List<String> photoUrls; // for GET response
}
