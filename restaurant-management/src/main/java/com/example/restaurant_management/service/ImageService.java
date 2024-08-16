package com.example.restaurant_management.service;

import com.example.restaurant_management.model.Image;
import com.example.restaurant_management.repository.ImageRepo;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;
    private final MinioClient minioClient;
    @Value("${minio.bucket.name}")
    private String bucketName;

    public Image saveImage(MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] imageData = file.getBytes();

        try {
            // Upload image to MinIO
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(filename)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(contentType)
                                .build()
                );
            } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            // Save image to MySQL
            Image image = new Image();
            image.setName(filename);
            image.setType(contentType);
            image.setData(imageData); // Set the image data
            return imageRepo.save(image);

        } catch (MinioException | IOException e) {
            throw new IOException("Error occurred while uploading the image to MinIO", e);
        }
    }

    public Optional<Image> getImage(Long id) {
        return imageRepo.findById(id);
    }

    public ImageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public byte[] getImageData(String imageName) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(imageName)
                        .build());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

        } catch (MinioException | IOException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImageById(Long id) {
        Optional<Image> imageOptional = imageRepo.findById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            try {
                try {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(image.getName())
                                    .build()
                    );
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                imageRepo.deleteById(id);
            } catch (MinioException e) {
                throw new RuntimeException("Error occurred while deleting image from MinIO", e);
            }
        }
    }
}
