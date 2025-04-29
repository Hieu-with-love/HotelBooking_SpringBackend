package hcmute.edu.vn.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CloudinaryResponse {
    private String publicId;
    private String url;
}
