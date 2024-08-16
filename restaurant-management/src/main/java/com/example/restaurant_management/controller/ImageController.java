package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Image;
import com.example.restaurant_management.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            Image image = imageService.saveImage(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully: " + image.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.getImage(id);
        if (imageOptional.isPresent()) {
            String imageName = imageOptional.get().getName();
            byte[] imageData = imageService.getImageData(imageName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
