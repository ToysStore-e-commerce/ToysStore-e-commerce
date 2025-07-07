package store.toys.ecommerce.dtos.cloudinary;

import java.util.Map;

public class CloudinaryMapper {
    public static CloudinaryDTO fromMap(Map<?, ?> result) {
        return CloudinaryDTO.builder()
                .publicId((String) result.get("public_id"))
                .url((String) result.get("secure_url"))
                .build();
    }
}
