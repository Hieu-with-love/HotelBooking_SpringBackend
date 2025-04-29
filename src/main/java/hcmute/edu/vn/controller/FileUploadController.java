package hcmute.edu.vn.controller;

import hcmute.edu.vn.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final CloudinaryService cloudinaryService;

//    @PostMapping("/upload/image")
//    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
//                                         @RequestParam("folder") String folderName) throws Exception {
//        return ResponseEntity.ok(cloudinaryService.uploadFile(file, folderName));
//    }
//
//    @PostMapping("/upload/video")
//    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file,
//                                         @RequestParam("folder") String folderName) throws Exception {
//        return ResponseEntity.ok(cloudinaryService.uploadVideo(file, folderName));
//    }
}
