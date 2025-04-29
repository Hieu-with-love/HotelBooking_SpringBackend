package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    CloudinaryResponse uploadFile(MultipartFile file, String fileName, String folder) throws Exception;
    CloudinaryResponse uploadVideo(MultipartFile file, String fileName, String folder) throws Exception;
}
