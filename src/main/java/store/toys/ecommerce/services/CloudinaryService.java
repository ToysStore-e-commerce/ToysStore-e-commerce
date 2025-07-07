package store.toys.ecommerce.services;

import com.cloudinary.Cloudinary;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.toys.ecommerce.dtos.cloudinary.CloudinaryDTO;
import store.toys.ecommerce.dtos.cloudinary.CloudinaryMapper;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryDTO uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", "toysstore/product" + fileName));
            return CloudinaryMapper.fromMap(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file");
        }
    }
}
