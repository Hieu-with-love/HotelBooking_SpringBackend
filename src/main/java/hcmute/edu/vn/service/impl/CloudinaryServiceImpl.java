package hcmute.edu.vn.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hcmute.edu.vn.dto.response.CloudinaryResponse;
import hcmute.edu.vn.service.CloudinaryService;
import hcmute.edu.vn.utils.FileUploadUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Transactional
    @Override
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName, String folder) throws Exception {
        final String url = FileUploadUtils.generateFileName(fileName);
        final String extension = FilenameUtils.getExtension(fileName); // apache-commons-io
        final String publicId = folder + "/" + url.replace("." + extension, ""); // Xóa đuôi nếu có

        final Map result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", publicId
                )
        );

        String secureUrl = (String) result.get("secure_url");

        CloudinaryResponse cloudinaryResponse = CloudinaryResponse.builder()
                .publicId(publicId)
                .url(secureUrl)
                .build();

        return cloudinaryResponse;
    }

    @Override
    public CloudinaryResponse uploadVideo(MultipartFile file, String fileName, String folder) throws Exception {
        return null;
    }
}
