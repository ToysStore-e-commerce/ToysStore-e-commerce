package store.toys.ecommerce.dtos.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudinaryDTO {
    private String publicId;
    private String url;
}
