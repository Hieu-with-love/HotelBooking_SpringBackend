package hcmute.edu.vn.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageUtils {
    public static String getSavedUrl(MultipartFile file) throws IOException {
        // Get original file name and extension. Prevent security issue
        String originalFileName = file.getOriginalFilename();
        if (originalFileName==null || originalFileName.isEmpty()){
            throw new IOException("File name is invalid");
        }

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        if (extension.isEmpty() || !isValidExtension(extension)){
            throw new IOException("File extension is invalid");
        }

        // Create directory if not exist
        // Path save file, it must be in the same directory with the project
        String uploadsDir = System.getProperty("user.dir") + "/uploads/";
        File uploadsPath = new File(uploadsDir);
        if (!uploadsPath.exists()){
            uploadsPath.mkdirs();
        }

        // Create unique file name
        String newFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // Path destination file
        File dest = new File(uploadsDir + newFileName);
        try{
            file.transferTo(dest);
        }catch (IOException ex){
            throw new IOException("Error saving image" + ex.getMessage());
        }

        return "/uploads/" + newFileName;
    }

    private static boolean isValidExtension(String extension){
        return extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png");
    }
}
