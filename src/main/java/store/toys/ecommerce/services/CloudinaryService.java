package store.toys.ecommerce.services;

import com.cloudinary.Cloudinary;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.toys.ecommerce.dtos.cloudinary.CloudinaryResponseDTO;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponseDTO uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", "toysstore/product" + fileName));
            final String publicId = (String) result.get("public_id");
            final String url = (String) result.get("secure_url");
            return CloudinaryResponseDTO.builder()
                    .publicId(publicId)
                    .url(url)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file");
        }
    }
}
